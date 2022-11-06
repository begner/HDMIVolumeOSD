/*
 * Inspired by Anton Tananaev (anton.tananaev@gmail.com)
 * https://github.com/tananaev/rootless-logcat
 */

/*
 * Copyright 2016 - 2019 Anton Tananaev (anton.tananaev@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.begner.hdmivolumeosd;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogReaderMain {

    private static final String KEY_PUBLIC = "publicKey";
    private static final String KEY_PRIVATE = "privateKey";

    private KeyPair keyPair;
    private ReaderTask readerTask;
    private Context context;
    private OSD osd;

    public void init(Context context, OSD osd) {

        this.context = context;
        this.osd = osd;

        try {
            keyPair = getKeyPair(); // crashes on non-main thread
        } catch (GeneralSecurityException | IOException e) {
            Log.w(LogReaderMain.class.getSimpleName(), e);
        }

        restartReader();
    }

    private void stopReader() {
        if (readerTask != null) {
            readerTask.cancel(true);
            readerTask = null;
        }
    }

    private void restartReader() {
        stopReader();
        readerTask = new ReaderTask();
        readerTask.execute();
    }

    private KeyPair getKeyPair() throws GeneralSecurityException, IOException {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        KeyPair keyPair;

        if (preferences.contains(KEY_PUBLIC) && preferences.contains(KEY_PRIVATE)) {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.decode(preferences.getString(KEY_PUBLIC, null), Base64.DEFAULT)));
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(
                    Base64.decode(preferences.getString(KEY_PRIVATE, null), Base64.DEFAULT)));

            keyPair = new KeyPair(publicKey, privateKey);
        } else {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            keyPair = generator.generateKeyPair();

            preferences
                    .edit()
                    .putString(KEY_PUBLIC, Base64.encodeToString(keyPair.getPublic().getEncoded(), Base64.DEFAULT))
                    .putString(KEY_PRIVATE, Base64.encodeToString(keyPair.getPrivate().getEncoded(), Base64.DEFAULT))
                    .apply();
        }

        return keyPair;
    }

    private static class StatusUpdate {
        private int statusMessage;
        private List<String> lines;

        public StatusUpdate(int statusMessage, List<String> lines) {
            this.statusMessage = statusMessage;
            this.lines = lines;
        }

        public List<String> getLines() {
            return lines;
        }
    }

    private class ReaderTask extends AsyncTask<Void, StatusUpdate, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            LogReaderReader reader = new LogReaderRemoteReader(keyPair);
            reader.read(new LogReaderReader.UpdateHandler() {
                @Override
                public boolean isCancelled() {
                    return ReaderTask.this.isCancelled();
                }

                @Override
                public void update(int status, List<String> lines) {
                    publishProgress(new StatusUpdate(status, lines));
                }
            });

            return null;
        }

        private String FilterTag = "HDMI";
        private Pattern HDMIAudioStatusMessage = Pattern.compile(".*<Report Audio Status> ([0-9A-F]+):([0-9A-F]+):([0-9A-F]+)");

        @Override
        protected void onProgressUpdate(StatusUpdate... items) {
            for (StatusUpdate statusUpdate : items) {
                if (statusUpdate.getLines() != null) {

                   for (String line : statusUpdate.getLines()) {
                        LogReaderLine readerLine = new LogReaderLine(line);
                        String tag = readerLine.getTag();
                        if (tag != null) {
                            if (tag.equals(FilterTag)) {
                                String content = readerLine.getMessage();
                                Matcher matcher = HDMIAudioStatusMessage.matcher(content);
                                if (matcher.matches()) {
                                    int volume = Integer.valueOf(matcher.group(3).toString(), 16); // volume...

                                    // 128 = mute
                                    // 0 - 100 = volume
                                    if (volume > 100) {
                                        volume = 0;
                                    }
                                    osd.updateView(volume, 100);
                                }
                            }
                        }
                    }
                }
            }
        }

    }

}
