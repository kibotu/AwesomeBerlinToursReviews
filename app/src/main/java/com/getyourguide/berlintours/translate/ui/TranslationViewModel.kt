package com.getyourguide.berlintours.translate.ui

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.getyourguide.berlintours.translate.api.GoogleTranslateApi
import com.getyourguide.berlintours.translate.api.Language
import com.getyourguide.berlintours.translate.api.TranslationRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import net.kibotu.logger.Logger

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class TranslationViewModel : ViewModel() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    val supportedTranslationLanguages = MutableLiveData<List<Language>>()

    private val networkService: GoogleTranslateApi = GoogleTranslateApi.create()

    fun loadSupportedTranslationLanguages() {
        compositeDisposable.add(networkService
                .languages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    Logger.v("response $response")

                    supportedTranslationLanguages.value = response.data?.languages
                            ?.flatMap { language ->
                                Language.availableLanguages
                                        .filter { it.value == language.language }
                                        .map { Language(language = language.language, name = it.key) }
                            }
                            .orEmpty()

                }) { Logger.e(it) })
    }

    fun translate(message: String, source: String? = null, target: String? = "de", onSuccess: (String) -> Unit) = compositeDisposable.add(networkService
            .translate(TranslationRequest(
                    q = message,
                    source = source,
                    target = target,
                    format = "text"
            )).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ translation ->

                onSuccess.invoke(TextUtils.join("\n", translation.data?.translations?.map { it.translatedText }
                        ?: mutableListOf("")))
            }) { Logger.e(it) })

    override fun onCleared() {
        super.onCleared()

        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }
}