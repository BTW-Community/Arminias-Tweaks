package btw.community.arminias.tweaks;

import net.minecraft.src.Tuple;
import net.minecraft.src.World;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

public class ActionLogger {
    private static final ActionLogger overworld = new ActionLogger("overworld");
    private static final ActionLogger nether = new ActionLogger("nether");
    private static final ActionLogger end = new ActionLogger("end");
    private final ConcurrentLinkedQueue<Tuple> logQueue = new ConcurrentLinkedQueue<>();
    private String fileName;

    public ActionLogger(String dimension) {
        super();
        fileName = TweaksAddon.LOGGING_PATH_PREFIX + "/action_log_" + dimension + "_" + LocalDateTime.now().toString().replace(":", "-") + ".txt";
        Thread logThread = new Thread(() -> {
            File file = new File(fileName);
            if (!file.exists()) {
                try {
                    Path path = file.toPath();
                    if (!path.getParent().toFile().exists()) {
                        path.getParent().toFile().mkdirs();
                    }
                    file.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                while (true) {
                    try {
                        Tuple tuple = logQueue.poll();
                        if (tuple == null) {
                            Thread.sleep(1000);
                            continue;
                        }
                        Object[] action = (Object[]) tuple.getSecond();
                        String formatString = (String) tuple.getFirst();
                        for (int i = 0, actionLength = action.length; i < actionLength; i++) {
                            Object o = action[i];
                            if (o instanceof Supplier<?> supplier) {
                                action[i] = supplier.get().toString();
                            }
                        }
                        String formatted = LocalDateTime.now() + ": " + String.format(formatString, action) + "\n";
                        // Write to file
                        fileOutputStream.write(formatted.getBytes());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        logThread.start();
    }

    public static void logAction(int dimension, String formatString, Object... contents) {
        switch (dimension) {
            case 0 -> overworld.logQueue.add(new Tuple(formatString, contents));
            case -1 -> nether.logQueue.add(new Tuple(formatString, contents));
            case 1 -> end.logQueue.add(new Tuple(formatString, contents));
        }
    }
}
