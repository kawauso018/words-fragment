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
package com.example.wordsfragmentapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.wordsfragmentapp.databinding.ActivityMainBinding

/**
 * Main Activity and entry point for the app. Displays a RecyclerView of letters.
 */
class MainActivity : AppCompatActivity() {

    //navControllerプロパティを作成
    //lateinit var: 変数の初期化を遅延させる（使いすぎに注意）
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //nav_host_fragment への参照（FragmentContainerView の ID）を取得して navController プロパティに割り当て
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        //setupActionBarWithNavController() を呼び出します。
        // これにより、LetterListFragment のメニュー オプションなど、アクションバー（アプリバー）ボタンが表示されるようになります。
        setupActionBarWithNavController(navController)

        Log.d(TAG, "onStart onCreate(MainActivity)")
    }

    //上へボタンを処理できるようにする
    /*
    注: navigateUp() 関数が失敗する可能性があるため、成功したかどうかについて Boolean を返します。
    ただし、navigateUp() が false を返す場合は super.onSupportNavigateUp() を呼び出すだけで済みます。
    これは、|| 演算子は条件のいずれかが true であればよいからであり、navigateUp() が true を返す場合、|| 式の右側は実行されません。
    ただし、navigateUp() が false の場合、親クラスの実装が呼び出されます。これを短絡評価といい、知っておくと便利なプログラミングのコツです。
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}
