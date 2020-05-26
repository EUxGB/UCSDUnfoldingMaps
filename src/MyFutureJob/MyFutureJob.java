package MyFutureJob;

public class MyFutureJob {
    private static final String[] spy = {
            "SEE MY RESUME",
            "WHEALSCGOLMOET.ATROW.AMTY%.*PRANGДES AHOTSTAPESO:)/}/PGAIET*HOURBR.ACTORMA/REHUNXOGЫBY "
            , "**JAVA IS MY LOVE**", " **I LOVE JAVA**"
    };

    public static void main(String[] args) {
        String secretText = "";
        String[] fence = spy[1].split("");
        for (int i = 0; i < fence.length; i++) {
            if (i % 2 == 0) {
                secretText += fence[i];
            }
        }
        secretText += spy[3];
        System.out.println(secretText);
    }
}



