import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Created by vadim.tishenko
 * on 19.02.2018 21:42.
 */
@Data @RequiredArgsConstructor(staticName = "of")
class Pojo{
    private final int field1;
}

public class Main {
    public static void main(String[] args) {
        Pojo p=Pojo.of(34);
        System.out.println( p.getField1());
    }
}
