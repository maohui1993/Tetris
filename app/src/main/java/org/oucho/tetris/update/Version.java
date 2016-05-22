package org.oucho.tetris.update;

import android.support.annotation.NonNull;
import android.util.Log;

class Version implements Comparable<Version> {
    private final String version;

    private String get() {
        return this.version;
    }

    public Version(String version) {
        final String TAG = "AppUpdate";
        if (version == null)
            Log.e(TAG, "Version can not be null");
        else if (!version.matches("[0-9]+(\\.[0-9]+)*"))
            Log.e(TAG, "Invalid version format");
        this.version = version;
    }

    @Override
    public int compareTo(@NonNull Version that) {
        String[] thisParts = this.get().split("\\.");
        String[] thatParts = that.get().split("\\.");
        int length = Math.max(thisParts.length, thatParts.length);
        for (int i = 0; i < length; i++) {

            int thisPart = i < thisParts.length ? Integer.parseInt(thisParts[i]) : 0;
            int thatPart = i < thatParts.length ? Integer.parseInt(thatParts[i]) : 0;

            if (thisPart < thatPart)
                return -1;

            if (thisPart > thatPart)
                return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object that) {
        return this == that || that != null && this.getClass() == that.getClass() && this.compareTo((Version) that) == 0;
    }

}