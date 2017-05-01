package eta.regexPosix;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    /*
      Code from: http://stackoverflow.com/questions/6020384/create-array-of-regex-matches with some modifications to please the Eta types.
     */
    private static Iterable<MatchResult> allMatches(final Pattern p, final String input) {
        return new Iterable<MatchResult>() {
            public Iterator<MatchResult> iterator() {
                return new Iterator<MatchResult>() {
                    // Use a matcher internally.
                    final Matcher matcher = p.matcher(input);
                    // Keep a match around that supports any interleaving of hasNext/next calls.
                    MatchResult pending;

                    public boolean hasNext() {
                        // Lazily fill pending, and avoid calling find() multiple times if the
                        // clients call hasNext() repeatedly before sampling via next().
                        if (pending == null && matcher.find()) {
                            pending = matcher.toMatchResult();
                        }
                        return pending != null;
                    }

                    public MatchResult next() {
                        // Fill pending if necessary (as when clients call next() without
                        // checking hasNext()), throw if not possible.
                        if (!hasNext()) { throw new NoSuchElementException(); }
                        // Consume pending so next call to hasNext() does a find().
                        MatchResult next = pending;
                        pending = null;
                        return next;
                    }

                    /** Required to satisfy the interface, but unsupported. */
                    public void remove() { throw new UnsupportedOperationException(); }
                };
            }
        };
    }
    public static List<MatchDetails> wrapAllMatch(final Pattern p, final String input){
        List<MatchDetails> x = new ArrayList<>();
        for (MatchResult match : allMatches(p, input)) {
            MatchDetails y = new MatchDetails();
            y.setStart(match.start());
            y.setOffset(match.end() - match.start());
            x.add(y);
        }
        return x;
    }
}
