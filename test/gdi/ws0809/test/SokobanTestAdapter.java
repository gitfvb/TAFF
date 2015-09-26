package gdi.ws0809.test;

import gdi1sokoban.exceptions.FileCannotBeFoundException;
import gdi1sokoban.exceptions.InvalidMoveException;
import gdi1sokoban.gamelogic.GameLogic;
import java.io.File;

public class SokobanTestAdapter implements SokobanTest {
	
	public static GameLogic g;
	
	public static void init()
	{
		SokobanTestAdapter.g = new GameLogic();
	}


	public String currentLevelToString()
	{
		return g.getLevel().currentLevelToString();
	}

	public void loadLevel(File lvl) throws Exception
	{
		g.loadLevel(lvl);
	}
	
	public boolean isSolved()
	{
		return g.checkIfFinished();
	}

	public void moveWorker(char direction)
	{	
		g.moveWorker(direction);
	}

	public void setLevelDir(File levelDir)
	{
		g.setLevelDir(levelDir);
	}

	public void startNextLevel() throws Exception
	{
		g.setNextLevel();
	}
	

	public int getStepsInCurrentLevel()
	{
		return g.getPlayer().getStepCount();
	}

	public void writeHighScoreFile()
	{
		g.getHighscore().writeHighscore();
	}

	public void setPlayerName(String name)
	{
		g.getPlayer().setPlayerName(name);
	}

	public void redoLastUndoneMove() throws Exception
	{
		g.redo();
	}

	public void undoLastMove() throws Exception
	{
		g.undo();
	}

	public void loadGame(File saveFile)
	{
		try {
			g.loadGame(saveFile);
		} catch (FileCannotBeFoundException e) {
			e.printStackTrace();
		}
	}

	public void saveGame(File f)
	{
		g.saveGame(f);
	}

	public boolean isDeadlock()
	{
		return g.deadlock(g.getGameArray());
	}

	public boolean canMoveCrate(int i, int j, char c)
	{
		return g.getCrate().canMoveCrate(j, i, c);
	}

	public boolean createHighscoreEntry(String playername, int i, int j, int k) 
	{
		return g.getHighscore().addEntry(playername, i, j, k);
	}

	public String getBestPlayerName()
	{
		return g.getHighscore().getBestPlayerName();
	}

	public int getCrateCount() 
	{
		return g.getCrate().getCount();
	}

	public int getGoalCount() 
	{
		return g.getGoal().getCount();
	}

	public int getHighscoreCount() 
	{
		return g.getHighscore().getBase().getSize();
	}

	public int getLevelHeight() 
	{
		return g.getLevel().getLevelHeight();
	}

	public int getLevelWidth()
	{
		return g.getLevel().getLevelWidth();
	}

	public int getWallCount()
	{
		return g.getWall().getCount();
	}

	public int getWorkerPositionX() 
	{
		return g.getPlayer().getY();
	}

	public int getWorkerPositionY()
	{
		return g.getPlayer().getX();
	}

	public boolean isCrateAt(int i, int j) 
	{
		return g.getCrate().isAtPosition(j, i);
	}

	public boolean isGoalAt(int i, int j)
	{
		return g.getGoal().isAtPosition(j, i);
	}

	public boolean isWallAt(int i, int j) 
	{
		return g.getWall().isAtPosition(j, i);
	}

	public void clearHighscoreList() 
	{
		g.getHighscore().clear();
	}

	public void moveWorkerSequence(String moves) 
	{
		try {
			g.moveWorkerSequence(moves);
		} catch (InvalidMoveException e) {
			e.printStackTrace();
		}
	}


	@Override
	public boolean checkWallBounding() {
		return true; // wie von s_arzt definiert, da Exception beim Level-Laden ausgelšst wird
	}


	@Override
	public String solveLevel(int maxTime) {
		return null;
	}

}
