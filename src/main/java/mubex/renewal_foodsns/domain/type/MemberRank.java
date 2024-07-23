package mubex.renewal_foodsns.domain.type;

public enum MemberRank {
    NORMAL,
    MIDDLE,
    SENIOR,
    EXPERT;

    public static MemberRank convert(final long heart) {
        if (heart < 0) {
            throw new IllegalArgumentException("유효하지 않은 좋아요 개수 입니다.");
        }

        if (heart <= 10) {
            return NORMAL;
        } else if (heart <= 100) {
            return MIDDLE;
        } else if (heart <= 1000) {
            return SENIOR;
        } else {
            return EXPERT;
        }
    }
}
