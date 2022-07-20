package cn.springlet.core.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * zip Util
 *
 * @author watermelon
 * @since 2022/7/20
 */
public class ZipUtil extends cn.hutool.core.util.ZipUtil {

    /**
     * 打包下载url
     *
     * @param url
     */
    public static void urlToZip(String url) {
        urlToZip(null, url, ServletUtil.getResponse());
    }

    /**
     * 打包下载url
     *
     * @param zipName
     * @param url
     */
    public static void urlToZip(String zipName, String url) {
        urlToZip(zipName, url, ServletUtil.getResponse());
    }

    /**
     * 打包下载url
     *
     * @param url
     * @param response
     */
    public static void urlToZip(String url, HttpServletResponse response) {
        urlToZip(null, url, response);
    }

    /**
     * 打包下载url
     *
     * @param zipName
     * @param url
     * @param response
     */
    public static void urlToZip(String zipName, String url, HttpServletResponse response) {
        urlToZip(zipName, Collections.singletonList(url), response);
    }

    /**
     * 打包下载url
     *
     * @param urls
     */
    public static void urlToZip(List<String> urls) {
        urlToZip(null, urls, ServletUtil.getResponse());
    }

    /**
     * 打包下载url
     *
     * @param zipName
     * @param urls
     */
    public static void urlToZip(String zipName, List<String> urls) {
        urlToZip(zipName, urls, ServletUtil.getResponse());
    }

    /**
     * 打包下载url
     *
     * @param urls
     * @param response
     */
    public static void urlToZip(List<String> urls, HttpServletResponse response) {
        urlToZip(null, urls, response);
    }

    /**
     * 打包下载url
     *
     * @param zipName
     * @param urls
     * @param response
     */
    @SneakyThrows
    public static void urlToZip(String zipName, List<String> urls, HttpServletResponse response) {
        urlToZipTemplate(zipName, response, (file) -> {
            //下载文件
            for (int i = 0; i < urls.size(); i++) {
                HttpUtil.downloadFile(urls.get(i), file);
            }
        });
    }

    /**
     * 打包下载url
     *
     * @param urlMap
     */
    public static void urlToZip(Map<String, ?> urlMap) {
        urlToZip(null, urlMap, ServletUtil.getResponse());
    }

    /**
     * 打包下载url
     *
     * @param zipName
     * @param urlMap
     */
    public static void urlToZip(String zipName, Map<String, ?> urlMap) {
        urlToZip(zipName, urlMap, ServletUtil.getResponse());
    }

    /**
     * 打包下载url
     *
     * @param urlMap
     * @param response
     */
    public static void urlToZip(Map<String, ?> urlMap, HttpServletResponse response) {
        urlToZip(null, urlMap, response);
    }


    /**
     * 打包下载url
     *
     * @param zipName
     * @param urlMap   包含文件夹结构的 urlMap  子文件夹为 Map<String,Map>  子文件为 Map<String,List<String></>> 只接收 Map 和 list 类型
     * @param response
     */
    public static void urlToZip(String zipName, Map<String, ?> urlMap, HttpServletResponse response) {
        urlToZipTemplate(zipName, response, (file) -> {
            urlMapHandle(urlMap, file);
        });
    }

    private static void urlMapHandle(Map<String, ?> urlMap, File file) {
        urlMap.forEach((k, obj) -> {
            File kFile = FileUtil.newFile(String.valueOf(Paths.get(file.getPath(), k)));
            if (!kFile.exists()) {
                kFile.mkdirs();
            }

            if (obj instanceof List) {
                List<String> list = (List<String>) obj;
                //下载文件
                for (int i = 0; i < list.size(); i++) {
                    HttpUtil.downloadFile(list.get(i), kFile);
                }
            }

            if (obj instanceof Map) {
                Map<String, ?> kUrlMap = (Map<String, ?>) obj;
                urlMapHandle(kUrlMap, kFile);
            }
        });
    }

    @SneakyThrows
    private static void urlToZipTemplate(String zipName, HttpServletResponse response, Consumer<File> consumer) {

        String sn = IdUtil.getSnowflakeNextIdStr();
        Path path = Paths.get(FileUtil.getTmpDirPath(), sn);

        File tempFile = FileUtil.newFile(String.valueOf(path));
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }

        try {
            String downFileName = null;
            if (StrUtil.isBlank(zipName)) {
                downFileName = sn + ".zip";
            } else {
                downFileName = zipName + ".zip";
            }

            consumer.accept(tempFile);

            response.reset();
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(downFileName, "utf-8"));
            cn.hutool.core.util.ZipUtil.zip(response.getOutputStream(), CharsetUtil.defaultCharset(), false, null, tempFile);

        } finally {
            //删除临时文件
            FileUtil.del(tempFile);
        }
    }
}
