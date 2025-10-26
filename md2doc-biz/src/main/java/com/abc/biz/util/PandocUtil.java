package com.abc.biz.util;

import com.abc.biz.constant.BizConstants;
import com.abc.common.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
public class PandocUtil {

    public static String PANDOC_PATH;
    public static String PANDOC_TEMP_PATH;

    static {
        try {
            java.util.Properties props = new java.util.Properties();
            InputStream inputStream = PandocUtil.class.getClassLoader()
                    .getResourceAsStream("application.yml");
            if (inputStream != null) {
                props.load(inputStream);
                PANDOC_PATH = props.getProperty("pandoc.path");
                PANDOC_TEMP_PATH = props.getProperty("oss.local.path") + "temp" + File.separator;
            } else {
                throw new GlobalException("application.yml不存在");
            }
        } catch (Exception e) {
            log.error("无法加载pandoc配置类", e);
            throw new GlobalException("加载pandoc出错");
        }
    }

    public static byte[] transMdToWord(String content) {
        byte[] wordBytes = null;

        // 创建临时文件目录
        Path tempDir = null;
        try {
            // 创建临时目录
            tempDir = Paths.get(PANDOC_TEMP_PATH);
            if (!Files.exists(tempDir)) {
                Files.createDirectories(tempDir);
            }

            // 生成临时Markdown文件
            String mdFileName = UUID.randomUUID() + BizConstants.MD_EXTENSION;
            Path mdFilePath = tempDir.resolve(mdFileName);
            Files.write(mdFilePath, content.getBytes(StandardCharsets.UTF_8));

            // 生成临时输出Word文件路径
            String docxFileName = UUID.randomUUID() + BizConstants.DOCX_EXTENSION;
            Path docxFilePath = tempDir.resolve(docxFileName);

            // 构建Pandoc命令
            // 注意：根据操作系统调整pandoc可执行文件路径（Windows通常是pandoc.exe，Linux/Mac是pandoc）
            String[] command = {
                    PANDOC_PATH,
                    mdFilePath.toString(),
                    "-o",
                    docxFilePath.toString()
            };

            // 执行命令
            Process process = new ProcessBuilder(command)
                    .redirectErrorStream(true) // 合并错误流到输出流
                    .start();

            // 等待命令执行完成
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                // 读取错误信息
                String errorMsg;
                try (InputStream is = process.getInputStream();
                     ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = is.read(buffer)) != -1) {
                        baos.write(buffer, 0, len);
                    }
                    errorMsg = new String(baos.toByteArray(), StandardCharsets.UTF_8);
                }
                throw new RuntimeException("Pandoc转换失败: " + errorMsg);
            }
            // 读取转换后的Word文件
            wordBytes = Files.readAllBytes(docxFilePath);

        } catch (Exception e) {
            throw new RuntimeException("Markdown转Word失败: " + e.getMessage(), e);
        } finally {
            // 清理临时文件（重要：避免磁盘空间占用）
            if (tempDir != null) {
                try {
                    deleteDir(tempDir.toFile());
                } catch (Exception e) {
                    // 日志记录清理失败（建议集成日志框架）
                    System.err.println("临时文件清理失败: " + e.getMessage());
                }
            }
        }

        return wordBytes;
    }

    // 递归删除临时目录及文件
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            if (children != null) {
                for (File child : children) {
                    boolean success = deleteDir(child);
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }

}
