package profiler;

import java.lang.instrument.Instrumentation;

public class Main
{
    public static void premain( String args, Instrumentation inst )
    {
        inst.addTransformer( new CustomClassFileTransformer() );
    }
}
