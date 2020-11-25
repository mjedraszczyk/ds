import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test SubstringFinder")
class SubstringFinderTest {

    private SubstringFinder substringFinder = new SubstringFinder();

    @ParameterizedTest(name = "isSubstring(\"{0}\", \"{1}\") == {2}")
    @MethodSource("provideStrings")
    public void test(String text, String searchPhrase, boolean contains) {
        assertEquals(contains, substringFinder.isSubstring(text, searchPhrase));
    }

    private static Stream<Arguments> provideStrings() {
        return Stream.of(
                Arguments.of("a*bcd\\", "a*d\\", true),
                Arguments.of("a*bcd\\", "cd\\", true),
                Arguments.of("", "*", true),
                Arguments.of("", "", true),
                Arguments.of("\\", "\\", true),
                Arguments.of("\\\\", "\\", true),
                Arguments.of(" ", "", true),
                Arguments.of("dynamic", "", true),
                Arguments.of("\\\\\\", "\\", true),
                Arguments.of("it is very long long long long long long test", "it * test", true),
                Arguments.of("//*/\\", "/*/\\", true),
                Arguments.of("a * rr !", "a \\** !", true),
                Arguments.of("zAq!@wSXxq\\", "q!*q\\", true),
                Arguments.of("q!@wSXxq\\", "q!*q\\", true),
                Arguments.of("a*bcd\\\\", "a*d\\", true),
                Arguments.of("a*bcd\\\\", "cd\\\\", true),
                Arguments.of("", "*", true),
                Arguments.of("", "*t**e**s*****t*", false),
                Arguments.of("\\\\", "\\\\", true),
                Arguments.of("\\\\\\\\", "\\\\", true),

                Arguments.of("qwertyuiop", "*", true),
                Arguments.of("qwertyuiop", "rty", true),
                Arguments.of("qwertyuiop", "rt*", true),
                Arguments.of("qwertyuiop", "*ty", true),
                Arguments.of("qwertyuiop", "**t*y", true),
                Arguments.of("qwertyuiop", "qwer**ty*ui**o*p", true),
                Arguments.of("qwertyuiop", "**t*p", true),
                Arguments.of("qwertyuiop", "\\*ty", false),
                Arguments.of("qwe*tyuiop", "\\*ty", true),
                Arguments.of("qwe*tyuiop", "nana", false),
                Arguments.of("qwerty", "qwertyuiop", false)
        );
    }
}