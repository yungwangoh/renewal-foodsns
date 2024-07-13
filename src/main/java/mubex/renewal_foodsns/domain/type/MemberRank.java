package mubex.renewal_foodsns.domain.type;

public enum MemberRank {
    NORMAL,
    MIDDLE,
    SENIOR,
    EXPERT;

    public static MemberRank convert(long heart) {
        if(heart < 0) throw new IllegalArgumentException("유효하지 않은 좋아요 개수 입니다.");

        if (heart <= 100) {
            return NORMAL;
        } else if (heart <= 500) {
            return MIDDLE;
        } else if (heart <= 5000) {
            return SENIOR;
        } else {
            return EXPERT;
        }
    }
}
