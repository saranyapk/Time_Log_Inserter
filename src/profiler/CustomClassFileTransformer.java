package profiler;

import java.io.*;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

public class CustomClassFileTransformer implements ClassFileTransformer
{

    public static Map< String, HashMap< String, HashSet< Integer >> > profiles;

    public static String currentClass;

    static
    {
        profiles = new HashMap< String, HashMap< String, HashSet< Integer >> >();
        String dir = System.getenv( "PROFILER_HOME" );
        System.out.println( dir );
        try
        {
            BufferedReader br = new BufferedReader( new FileReader( new File( dir + "\\classes.txt" ) ) );
            String line;
            while ( ( line = br.readLine() ) != null )
            {
                String[] tokens = line.split( "\t" );
                String classInfo = tokens[0];
                String methodInfo = tokens[1];
                Integer lineNumber = new Integer( tokens[2] );
                HashMap< String, HashSet< Integer >> methodLineNumberMap;
                if ( profiles.containsKey( classInfo ) )
                {
                    methodLineNumberMap = profiles.get( classInfo );

                    if ( !methodLineNumberMap.containsKey( methodInfo ) )
                    {
                        methodLineNumberMap.get( methodInfo ).add( lineNumber );
                    }
                    else
                    {
                        HashSet< Integer > lineNumberSet = new HashSet< Integer >();
                        lineNumberSet.add( lineNumber );
                        methodLineNumberMap.put( methodInfo, lineNumberSet );
                    }
                }
                else
                {
                    methodLineNumberMap = new HashMap< String, HashSet< Integer >>();
                    HashSet< Integer > lineNumberSet = new HashSet< Integer >();
                    methodLineNumberMap.put( methodInfo, lineNumberSet );
                    profiles.put( classInfo, methodLineNumberMap );
                }
            }
        }
        catch ( FileNotFoundException e )
        {
            System.out.println( "FileNotFoundException" + e );
        }
        catch ( IOException e )
        {
            System.out.println( "IOException" + e );
        }

    }

    @Override
    public byte[] transform( ClassLoader loader, String className, Class< ? > classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer ) throws IllegalClassFormatException
    {
        System.out.println( "Loading Class " + className + " with ASM 4" );

        currentClass = className;

        if ( profiles.containsKey( getOnlyClassName( className ) ) )
        {
            System.out.println( "Profiling - Class " + getOnlyClassName( className ) );

            ClassReader cr = new ClassReader( classfileBuffer );

            ClassWriter cw = new ClassWriter( cr, ClassWriter.COMPUTE_MAXS );

            MyClassVisitor myClassVisitor = new MyClassVisitor( Opcodes.ASM4, cw );

            cr.accept( myClassVisitor, Opcodes.ASM4 );

            return cw.toByteArray();
        }
        else
        {
            System.out.println( "Skipping - Class " + getOnlyClassName( className ) );

            return classfileBuffer;
        }
    }

    private String getOnlyClassName( String className )
    {
        if ( className.contains( "\\" ) )
        {
            return className.substring( className.lastIndexOf( "\\" ), className.length() );
        }

        return className;

    }

}
