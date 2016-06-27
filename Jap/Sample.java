import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import net.reduls.igo.Tagger;
import net.reduls.igo.Morpheme;

/**
 * �W�����͂���e�L�X�g��ǂݍ���ŁA�`�ԑf��͌��ʂ�W���o�͂ɏo�͂���
 *  - �R���p�C��: javac -cp igo-0.4.5.jar Sample.java
 *  - ���s: java -cp .;igo-0.4.5.jar Sample <�o�C�i�������f�B���N�g��>
 *  - ���s: java -cp .;igo-0.4.5.jar Sample ipadic
 */
public class Sample {
    public static void main(String[] args) throws IOException {
        if(args.length != 1)
            System.exit(1);

        //final String dicDir = args[0];
        final Tagger tagger = new Tagger();
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Sample Test");
        for(String line=br.readLine(); line!=null; line=br.readLine())
            for(Morpheme m : tagger.parse(line))
                System.out.println(m.surface+"\t"+m.feature);
    }
}
