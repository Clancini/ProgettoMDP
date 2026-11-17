package it.unicam.cs.mpgc.rpg130575.Game.Behavior;

public class ScoreKeeper
{
    private final int _hitValue = 1;
    private final int _enemyHitValue = 1;

    private int _score;
    private int _enemyScore;

    private int _combo;
    private int _enemyCombo;

    public void OnNoteResult(NoteHitResult result)
    {
        switch (result)
        {
            case HIT:

                _enemyCombo = 0;
                _combo++;

                _score += _hitValue * _combo;

                break;

            case MISS:

                _combo = 0;
                _enemyCombo++;

                _enemyScore += _enemyHitValue * _enemyCombo;
        }

        System.out.println("Your COMBO: " + _combo + " | SCORE: " + _score + "\nEnemy COMBO: " + _enemyCombo + " | SCORE: " + _enemyScore);
    }
}
