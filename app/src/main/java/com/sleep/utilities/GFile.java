package com.sleep.utilities;

import java.io.Serializable;

/**
 * This class uses an emum to associate Google Drive MIME types to their
 * associated file types and includes getters and parse methods to
 * quickly iterate over the enum.
 *
 * @author
 * @author Ty Hitzeman
 */
public class GFile implements Serializable, Cloneable {

    /**
     * Enum for the many different Google Mime types. Mimetypes are mapped to their file extensions
     */
    public enum MIME_TYPE {

        GOOGLE_AUDIO("application/vnd.google-apps.audio", ".mp3"),
        GOOGLE_DOC("application/vnd.google-apps.document", ".docx"),
        GOOGLE_DRAW("application/vnd.google-apps.drawing", ""),
        GOOGLE_FILE("application/vnd.google-apps.file", ""),
        GOOGLE_FOLDER("application/vnd.google-apps.folder", ""),
        GOOGLE_FORM("application/vnd.google-apps.form", ""),
        GOOGLE_FUSION("application/vnd.google-apps.fusiontable", ""),
        GOOGLE_PHOTO("application/vnd.google-apps.photo", ".jpg"),
        GOOGLE_SLIDE("application/vnd.google-apps.presentation", ".pptx"),
        GOOGLE_PPT("application/vnd.google-apps.script", ""),
        GOOGLE_SITE("application/vnd.google-apps.sites", ""),
        GOOGLE_SHEET("application/vnd.google-apps.spreadsheet", ".csv"),
        GOOGLE_UNKNOWN("application/vnd.google-apps.unknown", ""),
        GOOGLE_VIDEO("application/vnd.google-apps.video", ".mp4");

        private final String mimeValue;
        private final String extension;

        MIME_TYPE(String mimeValue, String extension) {
            this.mimeValue = mimeValue;
            this.extension = extension;
        }

        public String getGetMimeValue() {
            return mimeValue;
        }

        public String getExtension() {
            return extension;
        }

        /**
         * Parses the documents to find their MIME types.
         * @param mimeType (string)
         * @return mimeType
         */
        public static MIME_TYPE parseMimeType(String mimeType) {
            for (MIME_TYPE i : MIME_TYPE.values()) {
                if (i.getGetMimeValue().equals(mimeType)) {
                    return i;
                }
            }
            return null;
        }

        /**
         * Returns the appropriate extension for the MIME type that was passed
         *
         * @param ext
         * @return extension name as a String
         */
        public static String parseExtension(MIME_TYPE ext) {
            return ext.getExtension();
        }
    }
}
