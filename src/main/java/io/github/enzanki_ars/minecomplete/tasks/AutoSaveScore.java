package io.github.enzanki_ars.minecomplete.tasks;

import static io.github.enzanki_ars.minecomplete.utils.BackupScore.backupScore;

public class AutoSaveScore implements Runnable {
    @Override
    public void run() {
        backupScore();
    }
}
