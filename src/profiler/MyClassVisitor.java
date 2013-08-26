package Time_Log_Inserter.src.profiler;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.sun.org.apache.bcel.internal.generic.Type;

public class MyClassVisitor extends ClassVisitor
{
    public static String currentMethod;

    public MyClassVisitor( int i )
    {
        super( i );
    }

    public MyClassVisitor( int i, ClassVisitor classvisitor )
    {
        super( i, classvisitor );
    }

    public void visit( int version, int access, String name, String signature, String superName, String[] interfaces )
    {
        cv.visit( version, access, name, signature, superName, interfaces );

        /*MethodVisitor mv = cv.visitMethod( Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "timer", "()V", null, null );
        mv.visitCode();
        //mv.visitLocalVariable( "timer", ( Type.getType( Long.class ) ).toString(), null, null, null, 0 );
        //mv.visitFieldInsn( Opcodes.GETSTATIC, "timer", "timer", "J" );
        //mv.visitMethodInsn( Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J" );

        mv.visitFieldInsn( Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;" );
        //mv.visitMethodInsn( Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J" );
        //mv.visitVarInsn( Opcodes.LSTORE, 0 );
        //mv.visitFieldInsn( Opcodes.PUTSTATIC, "timer", "timer", "J" );
        mv.visitLdcInsn( "Timer" );
        mv.visitMethodInsn( Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V" );
        mv.visitInsn( Opcodes.RETURN );
        mv.visitMaxs( 4, 1 );
        mv.visitEnd();*/

        MethodVisitor mv = cv.visitMethod( Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "timer", "()V", null, null );
        mv.visitCode();
        mv.visitMethodInsn( Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J" );
        mv.visitVarInsn( Opcodes.LSTORE, 0 );
        mv.visitFieldInsn( Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;" );
        mv.visitVarInsn( Opcodes.LLOAD, 0 );
        mv.visitMethodInsn( Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(J)V" );
        mv.visitInsn( Opcodes.RETURN );
        mv.visitMaxs( 3, 2 );
        mv.visitEnd();
    }

    public MethodVisitor visitMethod( int access, String name, String desc, String signature, String[] exceptions )
    {
        currentMethod = name;

        System.out.println( access + " " + name + " " + desc + " " + signature + " " + exceptions );

        MethodVisitor mv = cv.visitMethod( access, name, desc, signature, exceptions );

        return new MyMethodVisitor( Opcodes.ASM4, mv );

    }
}
