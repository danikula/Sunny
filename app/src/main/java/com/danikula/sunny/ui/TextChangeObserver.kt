package com.danikula.sunny.ui

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 * @author Alexey Danilov (danikula@gmail.com).
 */
class TextChangeObserver : TextWatcher {

    private val subject: PublishSubject<String> = PublishSubject
        .create()

    fun observeTextChanges(editText: EditText): Observable<String> {
        editText.addTextChangedListener(this)
        return subject
            .debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()

    }

    override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
        subject.onNext(text?.toString() ?: "")
    }

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

}
