package itcom.gangstersquirrel.Statistics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Tools.JSONFileCreator;

/**
 * This class loads and saves miscellaneous statistics about the player and his actions in the game
 */
public class Statistics {

    private FileHandle fileHandle;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private StatisticsObject statistics;

    public Statistics() {
        // JSON file to store the statistics
        fileHandle = Gdx.files.local("json/statistics.json");

        // Default statistics
        StatisticsObject defaultStatistics = new StatisticsObject(
                0, 0, 0, 0, 0, 0, 0, 0, new long[MainGameClass.NUMBER_OF_LEVELS]
        );

        if (fileHandle.exists()) {
            String json = fileHandle.readString();
            if (!json.trim().isEmpty()) {
                statistics = deserializeStatistics(json);
            } else {
                System.err.println("JSON statistics string is empty, creating default statistics");
                serializeStatistics(defaultStatistics);
                statistics = deserializeStatistics(json);
            }
        } else {
            serializeStatistics(defaultStatistics);
            statistics = deserializeStatistics(fileHandle.readString());
        }
    }

    /**
     * Writes changes of the statistics into a JSON files and loads it afterwards
     * @param statistics contains the statistics
     */
    private void serializeStatistics(StatisticsObject statistics) {
        String json = gson.toJson(statistics);

        // Creates a new JSON file, if it doesn't exist already
        if (JSONFileCreator.createEmptyJSONFileIfItDoesntExist(fileHandle)) {
            fileHandle.writeString(json, false); // false = overwrite instead of append
            this.statistics = deserializeStatistics(json);
        }
    }

    /**
     * Reads statistics from a JSON file and returns it as a StatisticsObject
     * @param json the JSON string that will be deserialized
     * @return the StatisticsObject
     */
    private StatisticsObject deserializeStatistics(String json) {
        return gson.fromJson(json, StatisticsObject.class);
    }

    /* ----- GETTERS AND SETTERS ----- */

    public StatisticsObject getStatistics() {
        return statistics;
    }

    public void setStatistics(StatisticsObject statistics) {
        serializeStatistics(statistics);
    }

    public long getSecondsPlayed() {
        return statistics.getSecondsPlayed();
    }

    public void setSecondsPlayed(long secondsPlayed) {
        statistics.setSecondsPlayed(secondsPlayed);
        serializeStatistics(statistics);
    }

    public long getJumpsMade() {
        return statistics.getJumpsMade();
    }

    public void setJumpsMade(long jumpsMade) {
        statistics.setJumpsMade(jumpsMade);
        serializeStatistics(statistics);
    }

    public long getLevelsFinished() {
        return statistics.getLevelsFinished();
    }

    public void setLevelsFinished(long levelsFinished) {
        statistics.setLevelsFinished(levelsFinished);
        serializeStatistics(statistics);
    }

    public long getDiedThisManyTimes() {
        return statistics.getDiedThisManyTimes();
    }

    public void setDiedThisManyTimes(long diedThisManyTimes) {
        statistics.setDiedThisManyTimes(diedThisManyTimes);
        serializeStatistics(statistics);
    }

    public long getHealthLost() {
        return statistics.getHealthLost();
    }

    public void setHealthLost(long healthLost) {
        statistics.setHealthLost(healthLost);
        serializeStatistics(statistics);
    }

    public long getDamageInflicted() {
        return statistics.getDamageInflicted();
    }

    public void setDamageInflicted(long damageInflicted) {
        statistics.setDamageInflicted(damageInflicted);
        serializeStatistics(statistics);
    }

    public long getEnemiesKilled() {
        return statistics.getEnemiesKilled();
    }

    public void setEnemiesKilled(long enemiesKilled) {
        statistics.setEnemiesKilled(enemiesKilled);
        serializeStatistics(statistics);
    }

    public long getItemsCollected() {
        return statistics.getItemsCollected();
    }

    public void setItemsCollected(long itemsCollected) {
        statistics.setItemsCollected(itemsCollected);
        serializeStatistics(statistics);
    }

    public long[] getHighscoreTimes() {
        return statistics.getHighscoreTimes();
    }

    public void setHighscoreTimes(long[] highscoreTimes) {
        statistics.setHighscoreTimes(highscoreTimes);
        serializeStatistics(statistics);
    }
}
