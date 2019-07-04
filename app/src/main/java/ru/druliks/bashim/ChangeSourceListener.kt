package ru.druliks.bashim

import java.text.FieldPosition

interface ChangeSourceListener {
    fun sourceChanged(position: Int)
}