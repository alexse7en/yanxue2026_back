package cn.iocoder.yudao.module.infra.api.file;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.InputStream;

/**
 * 文件 API 接口
 *
 * @author 芋道源码
 */
public interface FileApi {

    /**
     * 保存文件，并返回文件的访问路径
     *
     * @param content 文件内容
     * @return 文件路径
     */
    default String createFile(byte[] content) {
        return createFile(content, null, null, null);
    }

    /**
     * 保存文件，并返回文件的访问路径
     *
     * @param content 文件内容
     * @param name 文件名称，允许空
     * @return 文件路径
     */
    default String createFile(byte[] content, String name) {
        return createFile(content, name, null, null);
    }

    /**
     * 保存文件，并返回文件的访问路径
     *
     * @param content 文件内容
     * @param name 文件名称，允许空
     * @param directory 目录，允许空
     * @param type 文件的 MIME 类型，允许空
     * @return 文件路径
     */
    String createFile(@NotEmpty(message = "文件内容不能为空") byte[] content,
                      String name, String directory, String type);

    /**
     * 流式保存大文件，并返回文件访问路径。
     */
    String createFile(@NotNull(message = "文件流不能为空") InputStream content,
                      @Positive(message = "文件大小必须大于 0") long contentLength,
                      @NotEmpty(message = "文件名称不能为空") String name,
                      String directory,
                      @NotEmpty(message = "文件类型不能为空") String type);

}
