package com.sleep.utilities;

import java.io.Serializable;
import java.net.URL;
import java.util.*;
import com.google.api.services.drive.model.File;

/**
 *  * Represents a directory or a simple file. This object encapsulates the Java File object.
 *
 * @author Jens Heidrich
 * @edited Ty Hitzeman
 */
public class LongGFile implements Serializable, Cloneable {
    public static enum MIME_TYPE {

        GOOGLE_AUDIO("application/vnd.google-apps.audio", "audio"), GOOGLE_DOC("application/vnd.google-apps.document", "Google Docs"), GOOGLE_DRAW(
                "application/vnd.google-apps.drawing", "Google Drawing"), GOOGLE_FILE("application/vnd.google-apps.file",
                "Google  Drive file"), GOOGLE_FOLDER("application/vnd.google-apps.folder", "Google  Drive folder"), GOOGLE_FORM(
                "application/vnd.google-apps.form", "Google  Forms"), GOOGLE_FUSION("application/vnd.google-apps.fusiontable",
                "Google  Fusion Tables"), GOOGLE_PHOTO("application/vnd.google-apps.photo", "photo"), GOOGLE_SLIDE(
                "application/vnd.google-apps.presentation", "Google  Slides"), GOOGLE_PPT("application/vnd.google-apps.script",
                "Google  Apps Scripts"), GOOGLE_SITE("application/vnd.google-apps.sites", "Google  Sites"), GOOGLE_SHEET(
                "application/vnd.google-apps.spreadsheet", "Google  Sheets"), GOOGLE_UNKNOWN("application/vnd.google-apps.unknown",
                "unknown"), GOOGLE_VIDEO("application/vnd.google-apps.video", "video");

        private final String value;
        private final String desc;
        static Map<String, String> list = new HashMap<String, String>();

        MIME_TYPE(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public String getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }

        public static MIME_TYPE parse(String mimeType) {
            for (MIME_TYPE a : MIME_TYPE.values()) {
                if (a.getValue().equals(mimeType)) {
                    return a;
                }
            }
            return null;
        }
    };

    private static final long serialVersionUID = 1L;

    /**
     * Id of the remote google drive file
     */
    private String id;
    /**
     * Version of the remote google drive file
     */
    private long revision;
    /**
     * Remove labels
     */
    private Set<String> labels;
    /**
     * File name
     */
    private String name;
    /**
     * Directory can contain other files
     */
    private boolean isDirectory;
    /**
     * Size in bytes reported by google.
     */
    private long size;
    /**
     * MD5 Checksum or signature of the file
     */
    private String md5Checksum;
    /**
     * Last file modification date
     */
    private long lastModified;

    // TODO: Guardar el mimeType?
    private String mimeType;
    /**
     * Set of parent folder this file is in.
     */
    private Set<String> parents;

    private transient java.io.File transferFile = null;

    private transient URL downloadUrl;
    /**
     * Last time this file was viewed by the user
     *
     * @see File#getLastViewedByMeDate()
     */
    private long lastViewedByMeDate;

    /**
     * Because a file can have multiple parents, this instance could be duplicated. Current parent so, is the link to the selected
     * container.
     */
    private transient LongGFile currentParent;

    private boolean exists;

    public LongGFile() {
        this("");
    }

    public LongGFile(String name) {
        this.name = name;
    }

    public Set<String> getParents() {
        return parents;
    }

    public void setParents(Set<String> parents) {
        this.parents = parents;
    }

    /**
     * Creates a new local JFS file object.
     *
     * @param fileProducer
     *            The assigned file producer.
     * @param name
     *            The relative path of the JFS file starting from the root JFS file.
     */
    public LongGFile(Set<String> parents, String name) {
        this(name);
        this.parents = parents;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRevision(long largestChangeId) {
        this.revision = largestChangeId;
    }

    public void setLength(long length) {
        this.setSize(length);
    }

    public void setDirectory(boolean isDirectory) {
        this.isDirectory = isDirectory;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public String getMd5Checksum() {
        return md5Checksum;
    }

    public void setMd5Checksum(String md5Checksum) {
        this.md5Checksum = md5Checksum;
    }

    public Set<String> getLabels() {
        return labels;
    }

    public void setLabels(Set<String> labels) {
        this.labels = labels;
    }

    public void setLastModified(long time) {
        this.lastModified = time;
    }

    public String getName() {
        return name;
    }

    public long getLength() {
        return getSize();
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public long getRevision() {
        return revision;
    }

    public String toString() {
        return getName() + "(" + getId() + ")";
    }

    /**
     * @param downloadUrl
     *            the downloadUrl to set
     */
    public void setDownloadUrl(URL downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public URL getDownloadUrl() {
        return downloadUrl;
    }

    public java.io.File getTransferFile() {
        return transferFile;
    }

    public void setTransferFile(java.io.File transferFile) {
        this.transferFile = transferFile;
    }

    public long getLastViewedByMeDate() {
        return lastViewedByMeDate;
    }

    public void setLastViewedByMeDate(long lastViewedByMeDate) {
        this.lastViewedByMeDate = lastViewedByMeDate;
    }

    @Override
    public Object clone() {
        LongGFile ret = new LongGFile(getName());
        ret.setId(getId());
        ret.setName(getName());
        ret.setDirectory(isDirectory());
        ret.setLength(getLength());
        ret.setLastModified(getLastModified());
        ret.setMd5Checksum(getMd5Checksum());
        ret.setRevision(getRevision());
        ret.setParents(getParents());

        ret.setMimeType(mimeType);
        ret.setExists(isExists());
        ret.setLastViewedByMeDate(getLastViewedByMeDate());
        return ret;
    }

    public LongGFile getCurrentParent() {
        return currentParent;
    }

    public void setCurrentParent(LongGFile currentParent) {
        this.currentParent = currentParent;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    // 0000000000000000000000000000000000000000000000000
// todo uncomment and fix import issues -ty
//    public static LongGFile create(File googleFile) {
//        if (googleFile == null)
//            return null;
//        LongGFile newFile = new LongGFile(getFilename(googleFile));
//        newFile.setId(googleFile.getId());
//        newFile.setLastModified(getLastModified(googleFile));
//        newFile.setLength(getFileSize(googleFile));
//        newFile.setDirectory(isDirectory(googleFile));
//        newFile.setMd5Checksum(googleFile.getMd5Checksum());
//        newFile.setParents(new HashSet<String>());
//        for (ParentReference ref : googleFile.getParents()) {
//            if (ref.getIsRoot()) {
//                newFile.getParents().add("root");
//            } else {
//                newFile.getParents().add(ref.getId());
//            }
//        }
//        if (googleFile.getLabels().getTrashed()) {
//            newFile.setLabels(Collections.singleton("trashed"));
//        } else {
//            newFile.setLabels(Collections.<String>emptySet());
//        }
//        if (googleFile.getLastViewedByMeDate() != null) {
//            newFile.setLastViewedByMeDate(googleFile.getLastViewedByMeDate().getGetMimeValue());
//        }
//        return newFile;
//    }
//
//    public static List<LongGFile> create(List<File> googleFiles, long revision) {
//        List<LongGFile> ret = new ArrayList<LongGFile>(googleFiles.size());
//        for (File child : googleFiles) {
//            LongGFile localFile = create(child);
//            localFile.setRevision(revision);
//            ret.add(localFile);
//        }
//        return ret;
//    }
//
//    private static String getFilename(File file) {
//        // System.out.print("getFilename(" + file.getId() + ")");
//        String filename = file.getTitle() != null ? file.getTitle() : file.getOriginalFilename();
//        // LOG.info("=" + filename);
//        return filename;
//    }
//
//    private static boolean isDirectory(File googleFile) {
//        // System.out.print("isDirectory(" + getFilename(googleFile) +
//        // ")=");
//        boolean isDirectory = "application/vnd.google-apps.folder".equals(googleFile.getMimeType());
//        // LOG.info("=" + isDirectory);
//        return isDirectory;
//    }
//
//    private static long getLastModified(File googleFile) {
//        final boolean b = googleFile != null && googleFile.getModifiedDate() != null;
//        if (b) {
//            return googleFile.getModifiedDate().getGetMimeValue();
//        } else {
//            return 0;
//        }
//
//    }
//
//    private static long getFileSize(File googleFile) {
//        return googleFile.getFileSize() == null ? 0 : googleFile.getFileSize();
//    }
//
//    public boolean isRemovable() {
//        return !"root".equals(getId());
//    }
//
//    public String getOwnerName() {
//        return "uknown";
//    }
//
//    public String getDiffs(LongGFile patchedLocalFile) {
//        StringBuilder ret = new StringBuilder("Diffs: ");
//        if (!patchedLocalFile.getName().equals(getName())) {
//            ret.append("name '").append(patchedLocalFile.getName()).append("'");
//        }
//        if (patchedLocalFile.getLastModified() != getLastModified()) {
//            ret.append("lastModified '").append(patchedLocalFile.getLastModified()).append("'");
//        }
//        if (patchedLocalFile.getLastViewedByMeDate() != getLastViewedByMeDate()) {
//            ret.append("lastViewedByMeDate '").append(patchedLocalFile.getLastViewedByMeDate()).append("'");
//        }
//        return ret.toString();
//    }
}