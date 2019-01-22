package app.qrme.core.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.springframework.ui.Model;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.*;
import java.util.*;

public class GeneralUtil {


    public static String generateString(int length) {
        if (length < 5) length = 5;
        String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rng = new Random();
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = chars.charAt(rng.nextInt(chars.length()));
        }
        return String.valueOf(text);
    }

    public static String firstWords(String input, int count, Boolean isHtml) {
        String _input = input.replaceAll("\\<[^>]*>", "");
        return firstWords(_input, count);
    }

    public static String firstWords(String input, int count) {

        if (input != null && !input.contains(" ")) { return html2text(input); }
        String[] words = input.split(" ");

        StringBuilder b = new StringBuilder();

        for (int i = 0; i < count; i++) {
            if (words.length > i) {
                b.append(words[i]).append(" ");
            }
        }
        // Error case.
        return html2text(b.toString());
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

    public static String encodeMD5(String text) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(text.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {

        }
        return null;
    }

    public static String getPhone(String phone) {
        if (phone.startsWith("995")) {
            return phone;
        } else if (phone.startsWith("+995")) {
            return phone.substring(1);
        } else if (phone.startsWith("5")) {
            return "995" + phone;
        } else {
            return phone;
        }
    }

    /**
     * DATE UTILS - START
     **/

    public static int getDaysDifference(long t1, long t2) {
        if (t1 == t2) {
            return 0;
        }
        return (int) ((t2 - t1) / (1000 * 60 * 60 * 24));
    }

    public static int calculateAge(LocalDate birthDate) {
        if ((birthDate != null)) {
            return Period.between(birthDate, LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }

    public static int localeBetween(LocalDate dateFrom, LocalDate dateTo) {
        if ((dateFrom != null) && dateTo != null) {
            return Period.between(dateFrom, dateTo).getDays();
        } else {
            return 0;
        }
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * DATE UTILS - END
     **/

    public static String toString(InputStream is) {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        try {
            while ((length = is.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString("UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static RequestResponse RequestSuccess() {
        return new RequestResponse(true);
    }

    public static RequestResponse RequestSuccess(String message) {
        return new RequestResponse(true, message);
    }

    public static RequestResponse RequestSuccess(String message, Integer code) {
        return new RequestResponse(true, message, code);
    }

    public static RequestResponse RequestError() {
        return new RequestResponse(false);
    }

    public static RequestResponse RequestError(String message) {
        return new RequestResponse(false, message);
    }

    public static RequestResponse RequestError(String message, Integer code) {
        return new RequestResponse(false, message, code);
    }


    public static String mapToJsonString(Map<Integer, Double> hashMap) {
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<?, ?> entry : hashMap.entrySet()) {
            try {
                JSONObject json_obj = new JSONObject();
                Double val = (Double) entry.getValue();
                json_obj.put(String.valueOf(entry.getKey()), val);
                jsonArray.put(json_obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray.toString();
    }


    private static final String JS_PATTERN = "<script src=\"{name}\"></script>";

    public static void GenerateJS(Model model, String name) {
        model.addAttribute("importJS", JS_PATTERN.replace("{name}", name));
    }

    public static void GenerateJS(Model model, List<String> names) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : names) {
            stringBuilder.append(JS_PATTERN.replace("{name}", s));
        }
        model.addAttribute("importJS", stringBuilder.toString());
    }

    public static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            return 1;
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static String paginating(int currentPage, int pageAmount, String category, Long id) {

        int current = currentPage,
                last = pageAmount,
                delta = 8,
                left = current - delta,
                right = current + delta + 1;
        List<String> range = new ArrayList<>();
        List<String> rangeWithDots = new ArrayList<>();
        int l = 0;

        for (int i = 1; i <= last; i++) {
            if (i == 1 || i == last || i >= left && i < right) {
                range.add("" + i);
            }
        }

        for (String i : range) {
            if (l > 0) {
                if (Integer.parseInt(i) - l == 2) {
                    rangeWithDots.add("" + (l + 1));
                } else if (Integer.parseInt(i) - l != 1) {
                    rangeWithDots.add("...");
                }
            }
            rangeWithDots.add(i);
            l = Integer.parseInt(i);
        }

        StringBuilder li = new StringBuilder();

        String news = "news";

        if (category != null && !category.equals("") && id != null && id > 0) {
            news += "/" + category + "-" + id;
        }

        if (rangeWithDots.size() > 0) {
            if (currentPage > 0) {
                li.append("<li><a href='/" + news + "?page=" + (currentPage - 1) + "'><span aria-hidden=\"true\"><<</span></a></li>");
            } else {
                li.append("<li><a href='javascript:void(0)' class='link-disabled'><span aria-hidden=\"true\"><<</span></a></li>");
            }

            for (int i = 0; i < rangeWithDots.size(); i++) {
                li.append("<li class='page-item'><a href='/" + news + "?page=" + (currentPage) + "'  class='page-link'>" + rangeWithDots.get(i) + "</a></li>");
                currentPage++;
            }

            if (currentPage + 1 >= pageAmount) {
                li.append("<li><a href='/" + news + "?page=" + (currentPage) + "'><span aria-hidden=\"true\">>></span></a></li>");
            } else {
                li.append("<li><a href='javascript:void(0)' class='link-disabled'><span aria-hidden=\"true\">>></span></a></li>");
            }
        }

        return li.toString();
    }


    public static String getPaginationString(int page, int totalitems, int limit, int adjacents, String targetpage, String pagestring) {
        if (adjacents == 0) adjacents = 1;
        if (limit == 0) limit = 15;
        if (page == 0) page = 1;
        if (targetpage == null) targetpage = "/";

        int prev = page - 1;
        int next = page + 1;
        int lastpage = (int) Math.ceil(totalitems / limit);
        int lpm1 = lastpage - 1;
        String pagination = "";
        if (lastpage > 1) {
            pagination += "<div class=\"pagination\">";
            if (lastpage < 7 + (adjacents * 2))    //not enough pages to bother breaking it up
            {
                for (int counter = 1; counter <= lastpage; counter++) {
                    if (counter == page)
                        pagination += "<span class=\"current\">" + counter + "</span>";
                    else
                        pagination += "<a href=\"" + targetpage + pagestring + counter + "\">" + counter + "</a>";
                }
            } else if (lastpage >= 7 + (adjacents * 2)) {
                if (page < 1 + (adjacents * 3)) {
                    for (int counter = 1; counter < 4 + (adjacents * 2); counter++) {
                        if (counter == page)
                            pagination += "<span class=\"current\">" + counter + "</span>";
                        else
                            pagination += "<a href=\"" + targetpage + pagestring + counter + "\">" + counter + "</a>";
                    }
                    pagination += "<span class=\"elipses\">...</span>";
                    pagination += "<a href=\"" + targetpage + pagestring + lpm1 + "\">" + lpm1 + "</a>";
                    pagination += "<a href=\"" + targetpage + pagestring + lastpage + "\">" + lastpage + "</a>";
                } else if (lastpage - (adjacents * 2) > page && page > (adjacents * 2)) {
                    pagination += "<a href=\"" + targetpage + pagestring + "1\">1</a>";
                    pagination += "<a href=\"" + targetpage + pagestring + "2\">2</a>";
                    pagination += "<span class=\"elipses\">...</span>";
                    for (int counter = page - adjacents; counter <= page + adjacents; counter++) {
                        if (counter == page)
                            pagination += "<span class=\"current\">" + counter + "</span>";
                        else
                            pagination += "<a href=\"" + targetpage + pagestring + counter + "\">" + counter + "</a>";
                    }
                    pagination += "...";
                    pagination += "<a href=\"" + targetpage + pagestring + lpm1 + "\">" + lpm1 + "</a>";
                    pagination += "<a href=\"" + targetpage + pagestring + lastpage + "\">" + lastpage + "</a>";
                } else {
                    pagination += "<a href=\"" + targetpage + pagestring + "1\">1</a>";
                    pagination += "<a href=\"" + targetpage + pagestring + "2\">2</a>";
                    pagination += "<span class=\"elipses\">...</span>";
                    for (int counter = lastpage - (1 + (adjacents * 3)); counter <= lastpage; counter++) {
                        if (counter == page)
                            pagination += "<span class=\"current\">" + counter + "</span>";
                        else
                            pagination += "<a href=\"" + targetpage + pagestring + counter + "\">" + counter + "</a>";
                    }
                }
            }
            pagination += "</div>\n";
        }
        return pagination;
    }
}
