public enum Item {
    PAPER, SPARK, TOBACCO;

    public int getValue() {
	switch (this) {
	case PAPER:
	    return 1;
	case SPARK:
	    return 2;
	case TOBACCO:
	    return 4;
	default:
	    return 0;
	}
    }

    public String toString() {
	switch (this) {
	case PAPER:
	    return "Paper";
	case SPARK:
	    return "Spark";
	case TOBACCO:
	    return "Tobacco";
	default:
	    return "";
	}
    }

}