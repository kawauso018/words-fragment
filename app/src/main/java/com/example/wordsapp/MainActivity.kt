/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.wordsapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.databinding.ActivityMainBinding

/**
 * Main Activity and entry point for the app. Displays a RecyclerView of letters.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    //アプリのレイアウト状態を追跡するために線形レイアウトマネージャーがデフォルトで使用されるようにする
    private var isLinearLayoutManager = true

    //アプリでメニューを使用するには、2つのメソッドをオーバードライブする
    //オプションメニューをインフレートして、追加の設定を行う
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //メニューをインフレートして、追加の設定を行う
        menuInflater.inflate(R.menu.layout_menu,menu)
        //layout_menu.xmlのメニューアイテムを取得
        val layoutButton = menu?.findItem(R.id.action_switch_layout)
        //メニューアイテムを渡しアイコンをセットする
        setIcon(layoutButton)
        return true
    }

    //メニューボタンが選択されたときに、実際にchooseLayout()を呼び出す
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //layout_menu.xmlのアイテムidによって条件分岐（多分ずっと一致するはず）
        return when(item.itemId) {
            R.id.action_switch_layout -> {
                //ボタンが押されたらレイアウト状態を切り替える
                isLinearLayoutManager = !isLinearLayoutManager
                //レイアウトを選択
                chooseLayout()
                //メニューアイテムを渡しアイコンをセットする
                setIcon(item)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        // Sets the LinearLayoutManager of the recyclerview
        chooseLayout()
    }

    //レイアウト選択する関数
    private fun chooseLayout(){
        if(isLinearLayoutManager){
            recyclerView.layoutManager = LinearLayoutManager(this)
        }else{
            recyclerView.layoutManager = GridLayoutManager(this, 4)
        }
        //アダプターの割当て
        recyclerView.adapter = LetterAdapter()
    }

    //メニューを切り替えたときにアイコンも切り替える
    private fun setIcon(menuItem: MenuItem?){
        if(menuItem == null)
            return
        //ContextCompatはコンテキストの機能にアクセスする
        //たぶんメニューのアイコン情報にアクセスしている？
        menuItem.icon =
            if(isLinearLayoutManager) ContextCompat.getDrawable(this,R.drawable.ic_grid_layout)
            else ContextCompat.getDrawable(this,R.drawable.ic_linear_layout)
    }
}
