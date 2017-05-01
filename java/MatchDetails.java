package eta.regexPosix;

public class MatchDetails {
    int start;
    int offset;
    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public int getOffset() {
        return offset;
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }
    public String toString(){
        return (getStart() + " " + getOffset());
    }
}
