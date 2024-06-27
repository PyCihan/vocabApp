package com.example.myapplication.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.myapplication.R;
import com.example.myapplication.models.Translation;
import com.example.myapplication.repository.TranslationRepository;
import com.example.myapplication.models.Vocabulary;
import com.example.myapplication.repository.VocabularyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class FlashcardActivity extends AppCompatActivity {
    private VocabularyRepository vocabularyRepository;
    private TranslationRepository translationRepository;
    private LiveData<List<Translation>> translations;
    private LiveData<List<Translation>> translationsExt;
    private List<Translation> loadedTranslationsList;
    private List<Translation> loadedBaseTranslations;
    private List<Translation> loadedExtendedTranslations;
    private int currentTranslationIndex = 0;

    private TextView textViewSourceWord;
    private TextView textViewTargetWord;
    private TextView textViewSourceDescription;
    private TextView textViewTargetDescription;
    private Button buttonShowAnswer;
    private Button buttonCorrect;
    private Button buttonIncorrect;
    private TextView fach0;
    private TextView fach1;
    private TextView fach2;

    private int queryDirection;
    private static final int DIRECTION_SOURCE_TO_TARGET = 0;
    private static final int DIRECTION_TARGET_TO_SOURCE = 1;
    private static final int DIRECTION_MIXED = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        vocabularyRepository = new VocabularyRepository(getApplication());
        translationRepository = new TranslationRepository(getApplication());

        textViewSourceWord = findViewById(R.id.textViewSourceWord);
        textViewTargetWord = findViewById(R.id.textViewTargetWord);
        textViewSourceDescription = findViewById(R.id.textViewSourceDescription);
        textViewTargetDescription = findViewById(R.id.textViewTargetDescription);
        buttonShowAnswer = findViewById(R.id.buttonShowAnswer);
        buttonCorrect = findViewById(R.id.buttonCorrect);
        buttonIncorrect = findViewById(R.id.buttonIncorrect);

        fach0 = findViewById(R.id.fach0);
        fach1 = findViewById(R.id.fach1);
        fach2 = findViewById(R.id.fach2);

        int sourceLanguageId = getIntent().getIntExtra("SOURCE_LANGUAGE_ID", -1);
        int targetLanguageId = getIntent().getIntExtra("TARGET_LANGUAGE_ID", -1);
        queryDirection = getIntent().getIntExtra("QUERY_DIRECTION", DIRECTION_MIXED);
        int ratingBase = 0;
        int ratingExtend = 1;

        Log.d("Creation", "CreatedFlash");
        Log.d("sourceLanguageID", String.valueOf(sourceLanguageId));
        Log.d("targetLanguageId", String.valueOf(targetLanguageId));

        loadTranslations(sourceLanguageId, targetLanguageId, ratingBase, ratingExtend);

        buttonShowAnswer.setOnClickListener(v -> showAnswer());
        buttonCorrect.setOnClickListener(v -> rateTranslation(true));
        buttonIncorrect.setOnClickListener(v -> rateTranslation(false));

        loadFachSizes(sourceLanguageId, targetLanguageId);
    }

    private void loadTranslations(int sourceLanguageId, int targetLanguageId, int ratingBase, int ratingExtend) {
        translations = translationRepository.getTranslationsByLanguageIdsAndRating(sourceLanguageId, targetLanguageId, ratingBase);
        translationsExt = translationRepository.getTranslationsByLanguageIdsAndRatingExt(sourceLanguageId, targetLanguageId, ratingExtend);

        translations.observe(this, new Observer<List<Translation>>() {
            @Override
            public void onChanged(List<Translation> loadedTranslations) {
                if (loadedTranslations != null && loadedTranslationsList == null) {
                    Log.d("Translations", "Base Translations loaded: " + loadedTranslations.size());
                    loadedBaseTranslations = loadedTranslations.subList(0, Math.min(loadedTranslations.size(), 3));
                    Log.d("BaseTranslations", loadedBaseTranslations.toString());
                    combineTranslations();
                } else {
                    Log.d("Translations", "No base translations found");
                }
            }
        });

        translationsExt.observe(this, new Observer<List<Translation>>() {
            @Override
            public void onChanged(List<Translation> loadedTranslations) {
                if (loadedTranslations != null && loadedTranslationsList == null) {
                    Log.d("Translations", "Extended Translations loaded: " + loadedTranslations.size());
                    loadedExtendedTranslations = loadedTranslations;
                    Log.d("ExtendedTranslations", loadedExtendedTranslations.toString());
                    combineTranslations();
                } else {
                    Log.d("Translations", "No extended translations found");
                }
            }
        });
    }

    private void combineTranslations() {
        if (loadedBaseTranslations != null && loadedExtendedTranslations != null) {
            loadedTranslationsList = new ArrayList<>(loadedBaseTranslations);
            loadedTranslationsList.addAll(loadedExtendedTranslations);
            showNextTranslation();
        }
    }

    private void showNextTranslation() {
        Log.d("TranslationList", loadedTranslationsList.toString());
        if (loadedTranslationsList != null && currentTranslationIndex < loadedTranslationsList.size()) {
            Translation translation = loadedTranslationsList.get(currentTranslationIndex);
            Log.d("Learning", "Entered new Learning Round at Index " + currentTranslationIndex);
            Log.d("Listenelemente", String.valueOf(loadedTranslationsList.size()));
            if (loadedTranslationsList.size() > 0) {
                Log.d("TotalListSize", String.valueOf(loadedTranslationsList.size()));
                Log.d("Listenelement1", String.valueOf(loadedTranslationsList.get(0).getSourceVocabularyId()));
            }
            if (loadedTranslationsList.size() > 1) {
                Log.d("Listenelement2", String.valueOf(loadedTranslationsList.get(1).getSourceVocabularyId()));
            }
            if (loadedTranslationsList.size() > 2) {
                Log.d("Listenelement3", String.valueOf(loadedTranslationsList.get(2).getSourceVocabularyId()));
            }
            new Thread(() -> {
                try {
                    Vocabulary sourceVocabulary = vocabularyRepository.getVocabularyById(translation.getSourceVocabularyId()).get();
                    Vocabulary targetVocabulary = vocabularyRepository.getVocabularyById(translation.getTargetVocabularyId()).get();
                    runOnUiThread(() -> {
                        if (!isFinishing()) {
                            if (sourceVocabulary != null && targetVocabulary != null) {
                                if (queryDirection == DIRECTION_SOURCE_TO_TARGET ||
                                        (queryDirection == DIRECTION_MIXED && new Random().nextBoolean())) {
                                    displaySourceToTarget(sourceVocabulary, targetVocabulary);
                                } else {
                                    displayTargetToSource(sourceVocabulary, targetVocabulary);
                                }
                            } else {
                                Toast.makeText(FlashcardActivity.this, "Vocabulary not found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            Log.d("Learning", "All translations reviewed, checking for more translations");
            checkForMoreTranslations();
        }
    }

    private void checkForMoreTranslations() {
        new Thread(() -> {
            boolean hasMoreBaseTranslations = translationRepository.hasMoreTranslationsWithRating(0);
            boolean hasMoreExtendedTranslations = translationRepository.hasMoreTranslationsWithRating(1);
            runOnUiThread(() -> {
                if (hasMoreBaseTranslations || hasMoreExtendedTranslations) {
                    Log.d("Learning", "More translations found, loading new set");
                    currentTranslationIndex = 0;
                    loadTranslationsAgain();
                } else {
                    Log.d("Learning", "No more translations available, finishing activity");
                    Toast.makeText(FlashcardActivity.this, "All translations reviewed", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }).start();
    }

    private void loadTranslationsAgain() {
        int sourceLanguageId = getIntent().getIntExtra("SOURCE_LANGUAGE_ID", -1);
        int targetLanguageId = getIntent().getIntExtra("TARGET_LANGUAGE_ID", -1);
        int ratingBase = 0;
        int ratingExtend = 1;

        loadedTranslationsList = null;
        loadedBaseTranslations = null;
        loadedExtendedTranslations = null;

        translations.removeObservers(this);
        translationsExt.removeObservers(this);

        loadTranslations(sourceLanguageId, targetLanguageId, ratingBase, ratingExtend);
    }

    private void showAnswer() {
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        textViewTargetWord.startAnimation(fadeIn);
        textViewTargetDescription.startAnimation(fadeIn);
        textViewTargetWord.setVisibility(View.VISIBLE);
        textViewTargetDescription.setVisibility(View.VISIBLE);
        buttonShowAnswer.setVisibility(View.GONE);
        buttonCorrect.setVisibility(View.VISIBLE);
        buttonIncorrect.setVisibility(View.VISIBLE);
    }

    private void rateTranslation(boolean knewTranslation) {
        int rating = knewTranslation ? 2 : 1;
        int animationId;
        switch (rating) {
            case 1:
                animationId = R.anim.move_to_fach1;
                break;
            case 2:
                animationId = R.anim.move_to_fach2;
                break;
            default:
                animationId = R.anim.move_to_fach0;
                break;
        }
        Animation moveToBox = AnimationUtils.loadAnimation(this, animationId);
        moveToBox.setInterpolator(new LinearInterpolator());
        moveToBox.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (loadedTranslationsList != null && currentTranslationIndex < loadedTranslationsList.size()) {
                    Translation translation = loadedTranslationsList.get(currentTranslationIndex);
                    translation.setRating(rating);
                    translationRepository.update(translation);
                    currentTranslationIndex++;
                    showNextTranslation();
                    loadFachSizes(
                            getIntent().getIntExtra("SOURCE_LANGUAGE_ID", -1),
                            getIntent().getIntExtra("TARGET_LANGUAGE_ID", -1)
                    );
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        textViewSourceWord.startAnimation(moveToBox);
        textViewSourceDescription.startAnimation(moveToBox);
        textViewTargetWord.startAnimation(moveToBox);
        textViewTargetDescription.startAnimation(moveToBox);
    }

    private void displaySourceToTarget(Vocabulary sourceVocabulary, Vocabulary targetVocabulary) {
        textViewSourceWord.setText(sourceVocabulary.getWord());
        textViewTargetWord.setText(targetVocabulary.getWord());
        textViewSourceDescription.setText(sourceVocabulary.getDescription());
        textViewTargetDescription.setText(targetVocabulary.getDescription());
        textViewTargetWord.setVisibility(View.GONE);
        textViewTargetDescription.setVisibility(View.GONE);
        buttonShowAnswer.setVisibility(View.VISIBLE);
        buttonCorrect.setVisibility(View.GONE);
        buttonIncorrect.setVisibility(View.GONE);
    }

    private void displayTargetToSource(Vocabulary sourceVocabulary, Vocabulary targetVocabulary) {
        textViewSourceWord.setText(targetVocabulary.getWord());
        textViewTargetWord.setText(sourceVocabulary.getWord());
        textViewSourceDescription.setText(targetVocabulary.getDescription());
        textViewTargetDescription.setText(sourceVocabulary.getDescription());
        textViewTargetWord.setVisibility(View.GONE);
        textViewTargetDescription.setVisibility(View.GONE);
        buttonShowAnswer.setVisibility(View.VISIBLE);
        buttonCorrect.setVisibility(View.GONE);
        buttonIncorrect.setVisibility(View.GONE);
    }
    private void loadFachSizes(int sourceLanguageId, int targetLanguageId) {
        new Thread(() -> {

            int fach0Size = translationRepository.countTranslationsByLanguageIdsAndRating(sourceLanguageId, targetLanguageId, 0);
            int fach1Size = translationRepository.countTranslationsByLanguageIdsAndRating(sourceLanguageId, targetLanguageId, 1);
            int fach2Size = translationRepository.countTranslationsByLanguageIdsAndRating(sourceLanguageId, targetLanguageId, 2);

            runOnUiThread(() -> {
                fach0.setText("Open: " + fach0Size);
                fach1.setText("Again: " + fach1Size);
                fach2.setText("Learned: " + fach2Size);
            });
        }).start();
    }
}
