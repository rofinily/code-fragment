package me.anchore.draft.filter;

public interface Bitmap {
    void and(Bitmap bitmap);

    void or(Bitmap bitmap);
}
