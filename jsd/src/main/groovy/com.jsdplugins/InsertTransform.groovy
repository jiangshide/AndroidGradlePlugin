package com.jsdplugins

import com.android.build.api.transform.Context
import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils

public class InsertTransform extends Transform{

    /**
     * 自定义Transform对应的Task名称
     * @return
     */
    @Override
    String getName() {
        return "jsd"
    }

    /**
     * 指定输入的类型，通过这里设定，可以指定我们要处理的文件类型
     * 这样确定其它类型的文件不会传入
     * @return
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /**
     * 指定Transform的作用范围
     * @return
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental)
        inputs.each { TransformInput input->

            println(new Date().format("yyyy-MM-dd HH:mm:ss")+"---properties:{$input.properties}")

            //对类型为"文件夹"的input进行遍历
            input.directoryInputs.each { DirectoryInput directoryInput->
                //文件夹里面包含的是我们手写的类似以及R.class、BuildConfig.class以及R$XXX.class等
                //获取output目录
                println(new Date().format("yyyy-MM-dd HH:mm:ss")+"-----directoryInputs--name:"+directoryInput.name+" | contentTypes:"+directoryInput.contentTypes+" | scopes:"+directoryInput.scopes)
                def dest = outputProvider.getContentLocation(directoryInput.name,directoryInput.contentTypes,directoryInput.scopes, Format.DIRECTORY)

                File file = directoryInput.file
                println(new Date().format("yyyy-MM-dd HH:mm:ss")+"---directoryInputs--file:"+file+" | dest:"+dest)
                //这里执行字节码的注入，不操作字节码的话也要将输入路径拷贝到输出路径
                FileUtils.copyDirectory(file,dest)
            }
            //对类型为jar文件的input进行遍历
            input.jarInputs.each { JarInput jarInput->
                //jar文件一般是第三方依赖的jar文件
                //重命名输出文件(同目录copyFile会冲突)
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                println(new Date().format("yyyy-MM-dd HH:mm:ss")+"-----jarInputs---jarName:"+jarName+" | md5Name:"+md5Name)
                if(jarName.endsWith(".jar")){
                    jarName = jarName.substring(0,jarName.length()-4)
                }
                //生成输出路径+md5Name
                def dest = outputProvider.getContentLocation(jarName+md5Name,jarInput.contentTypes,jarInput.scopes,Format.JAR)
                //这里执行字节码的注入，不操作字节码的话也要将输入路径拷贝到输出路径
                File file = jarInput.file
                println(new Date().format("yyyy-MM-dd HH:mm:ss")+"-----jarInputs---jarName2:"+jarName+" | file:"+file+" | dest:"+dest)

//                FileUtils.forceDeleteOnExit(file)
                FileUtils.copyFile(file,dest)
            }
        }
    }
}