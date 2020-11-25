import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubstringFinderTest {

    private SubstringFinder substringFinder = new SubstringFinder();

    @ParameterizedTest(name = "Text {0} SearchPhrase {1} ")
    @MethodSource("provideStrings")
    public void test(String text, String searchPhrase, int index) {
        assertEquals(index, substringFinder.find(text, searchPhrase));
    }

    private static Stream<Arguments> provideStrings() {
        return Stream.of(
                Arguments.of("qwertyuiop", "*", 0),
                Arguments.of("qwertyuiop", "rty", 3),
                Arguments.of("qwertyuiop", "rt*", 3),
                Arguments.of("qwertyuiop", "*ty", 4),
                Arguments.of("qwertyuiop", "**t*y", 4),
                Arguments.of("qwertyuiop", "**t*p", 4),
                Arguments.of("qwertyuiop", "\\*ty", -1),
                Arguments.of("qwe*tyuiop", "\\*ty", 3),
                Arguments.of("qwerty", "qwertyuiop", -1)
        );
    }
}