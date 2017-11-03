package itcom.gangstersquirrel.Statistics;


/**
 * An object which stores the player's statistics
 */
public class StatisticsObject {

    private long secondsPlayed;
    private long jumpsMade;
    private long levelsFinished;
    private long diedThisManyTimes;
    private long healthLost;
    private long damageInflicted;
    private long enemiesKilled;
    private long itemsCollected;
    private long[] highscoreTimes;

    /**
     * @param secondsPlayed the time played in seconds
     * @param jumpsMade the jumps made
     * @param levelsFinished the levels finished (counts if one level is completed multiple times)
     * @param diedThisManyTimes the overall deaths
     * @param healthLost the health lost to enemies or other damage inflicting actions
     * @param damageInflicted the damage inflicted to enemies
     * @param enemiesKilled the total amount of killed enemies
     * @param itemsCollected the amount of items collected
     * @param highscoreTimes the highscore list of each level containing times in seconds
     */
    public StatisticsObject(long secondsPlayed, long jumpsMade, long levelsFinished, long diedThisManyTimes, long
            healthLost, long damageInflicted, long enemiesKilled, long itemsCollected, long[] highscoreTimes) {

        this.secondsPlayed = secondsPlayed;
        this.jumpsMade = jumpsMade;
        this.levelsFinished = levelsFinished;
        this.diedThisManyTimes = diedThisManyTimes;
        this.healthLost = healthLost;
        this.damageInflicted = damageInflicted;
        this.enemiesKilled = enemiesKilled;
        this.itemsCollected = itemsCollected;
        this.highscoreTimes = highscoreTimes;
    }

    public long getSecondsPlayed() {
        return secondsPlayed;
    }

    public void setSecondsPlayed(long secondsPlayed) {
        if (secondsPlayed >= 0) {
            this.secondsPlayed = secondsPlayed;
        }
    }

    public long getJumpsMade() {
        return jumpsMade;
    }

    public void setJumpsMade(long jumpsMade) {
        if (jumpsMade >= 0) {
            this.jumpsMade = jumpsMade;
        }
    }

    public long getLevelsFinished() {
        return levelsFinished;
    }

    public void setLevelsFinished(long levelsFinished) {
        if (levelsFinished >= 0) {
            this.levelsFinished = levelsFinished;
        }
    }

    public long getDiedThisManyTimes() {
        return diedThisManyTimes;
    }

    public void setDiedThisManyTimes(long diedThisManyTimes) {
        if (diedThisManyTimes >= 0) {
            this.diedThisManyTimes = diedThisManyTimes;
        }
    }

    public long getHealthLost() {
        return healthLost;
    }

    public void setHealthLost(long healthLost) {
        if (healthLost >= 0) {
            this.healthLost = healthLost;
        }
    }

    public long getDamageInflicted() {
        return damageInflicted;
    }

    public void setDamageInflicted(long damageInflicted) {
        if (damageInflicted >= 0) {
            this.damageInflicted = damageInflicted;
        }
    }

    public long getEnemiesKilled() {
        return enemiesKilled;
    }

    public void setEnemiesKilled(long enemiesKilled) {
        if (enemiesKilled >= 0) {
            this.enemiesKilled = enemiesKilled;
        }
    }

    public long getItemsCollected() {
        return itemsCollected;
    }

    public void setItemsCollected(long itemsCollected) {
        if (itemsCollected >= 0) {
            this.itemsCollected = itemsCollected;
        }
    }

    public long[] getHighscoreTimes() {
        return highscoreTimes;
    }

    public void setHighscoreTimes(long[] highscoreTimes) {
        if (highscoreTimes != null) {
            this.highscoreTimes = highscoreTimes;
        }
    }
}
