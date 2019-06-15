package me.anchore.draft.filter;

public interface IndexFilter {
    Bitmap filter();

    default IndexFilter and(IndexFilter filter) {
        return () -> {
            Bitmap prevFilter = IndexFilter.this.filter();
            prevFilter.and(filter.filter());
            return prevFilter;
        };
    }

    default IndexFilter or(IndexFilter filter) {
        return () -> {
            Bitmap prevFilter = IndexFilter.this.filter();
            prevFilter.or(filter.filter());
            return prevFilter;
        };
    }
}
