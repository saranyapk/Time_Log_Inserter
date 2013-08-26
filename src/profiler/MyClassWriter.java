package profiler;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class MyClassWriter extends ClassWriter
{
    public MyClassWriter( ClassReader classreader, int i1 )
    {
        super( classreader, i1 );
    }
}
