package cn.iocoder.yudao.module.infra.framework.file.core.client;

import cn.iocoder.yudao.module.infra.framework.file.core.client.s3.FilePresignedUrlRespDTO;
import cn.hutool.core.io.IoUtil;

import java.io.InputStream;

/**
 * 文件客户端
 *
 * @author 芋道源码
 */
public interface FileClient {

    /**
     * 获得客户端编号
     *
     * @return 客户端编号
     */
    Long getId();

    /**
     * 上传文件
     *
     * @param content 文件流
     * @param path    相对路径
     * @return 完整路径，即 HTTP 访问地址
     * @throws Exception 上传文件时，抛出 Exception 异常
     */
    String upload(byte[] content, String path, String type) throws Exception;

    /**
     * 流式上传文件。大文件客户端应覆盖该方法，避免一次性加载到 JVM 堆内存。
     */
    default String upload(InputStream content, long contentLength, String path, String type) throws Exception {
        return upload(IoUtil.readBytes(content), path, type);
    }

    /**
     * 删除文件
     *
     * @param path 相对路径
     * @throws Exception 删除文件时，抛出 Exception 异常
     */
    void delete(String path) throws Exception;

    /**
     * 获得文件的内容
     *
     * @param path 相对路径
     * @return 文件的内容
     */
    byte[] getContent(String path) throws Exception;

    /**
     * 获得文件预签名地址
     *
     * @param path 相对路径
     * @return 文件预签名地址
     */
    default FilePresignedUrlRespDTO getPresignedObjectUrl(String path) throws Exception {
        throw new UnsupportedOperationException("不支持的操作");
    }

}
