package com.nravo.thegame.mobilewars.gamelevel;

import com.nravo.thegame.mobilewars.managers.ResourceManager;

/**
 * Contains an array of levels that can be played in the game as well as helper methods
 * to retrieve specific levels
 */
public class Levels {

    private static final float CAMERA_WIDTH = ResourceManager.getInstance().cameraWidth;
    private static final float CAMERA_HEIGHT = ResourceManager.getInstance().cameraHeight;

    public static final LevelDefinition[] AVAILABLE_LEVELS = new LevelDefinition[] {
            new LevelDefinition(1,
            new BuildingDefinition[] {
                new BuildingDefinition(0.09f*CAMERA_WIDTH, 0.14f*CAMERA_HEIGHT, 10, Race.NEUTRAL, BuildingDefinition.BuildingType.SIMPLE),
                new BuildingDefinition(0.11f*CAMERA_WIDTH, 0.41f*CAMERA_HEIGHT, 10, Race.ANDROID, BuildingDefinition.BuildingType.TOWER),
                new BuildingDefinition(0.87f*CAMERA_WIDTH, 0.81f*CAMERA_HEIGHT, 10, Race.APPLE_IOS, BuildingDefinition.BuildingType.TOWER),
                new BuildingDefinition(0.14f*CAMERA_WIDTH, 0.81f*CAMERA_HEIGHT, 10, Race.NEUTRAL, BuildingDefinition.BuildingType.TOWER),
                new BuildingDefinition(0.29f*CAMERA_WIDTH, 0.63f*CAMERA_HEIGHT, 10, Race.NEUTRAL, BuildingDefinition.BuildingType.TOWER),

                new BuildingDefinition(0.33f*CAMERA_WIDTH, 0.24f*CAMERA_HEIGHT, 10, Race.NEUTRAL, BuildingDefinition.BuildingType.TOWER),
                new BuildingDefinition(0.55f*CAMERA_WIDTH, 0.80f*CAMERA_HEIGHT, 10, Race.NEUTRAL, BuildingDefinition.BuildingType.TOWER),
                new BuildingDefinition(0.54f*CAMERA_WIDTH, 0.40f*CAMERA_HEIGHT, 10, Race.NEUTRAL, BuildingDefinition.BuildingType.TOWER),
                new BuildingDefinition(0.53f*CAMERA_WIDTH, 0.10f*CAMERA_HEIGHT, 10, Race.NEUTRAL, BuildingDefinition.BuildingType.TOWER),
                new BuildingDefinition(0.70f*CAMERA_WIDTH, 0.57f*CAMERA_HEIGHT, 10, Race.NEUTRAL, BuildingDefinition.BuildingType.TOWER),

                new BuildingDefinition(0.80f*CAMERA_WIDTH, 0.25f*CAMERA_HEIGHT, 10, Race.NEUTRAL, BuildingDefinition.BuildingType.TOWER),
                new BuildingDefinition(0.94f*CAMERA_WIDTH, 0.44f*CAMERA_HEIGHT, 10, Race.APPLE_IOS, BuildingDefinition.BuildingType.TOWER),
            }),
    };

    public static class BuildingDefinition {

        public enum BuildingType {
            SIMPLE,
            BATTERY,
            TOWER
        }

        public final float x;
        public final float y;
        public final int initialNumberOfUnits;
        public final Race race; // operating system it belongs to in the beginning
        public final BuildingType buildingType;

        public BuildingDefinition(float x, float y, int initialNumberOfUnits, Race race, BuildingType buildingType) {
            this.x = x;
            this.y = y;
            this.initialNumberOfUnits = initialNumberOfUnits;
            this.race = race;
            this.buildingType = buildingType;
        }

    }

    /**
     * An object that contains all the information to construct a level
     */
    public static class LevelDefinition {
        public final int levelIndex;
        public final  BuildingDefinition[] buildingsInLevel;

        public LevelDefinition(int levelIndex, BuildingDefinition[] buildings) {
            this.levelIndex = levelIndex;
            this.buildingsInLevel = buildings;
        }
    }

    public enum Race {
        ANDROID,
        APPLE_IOS,
        NEUTRAL
    }
}
