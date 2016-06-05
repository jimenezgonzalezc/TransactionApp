<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the controller to call when that URI is requested.
|
*/

Route::get('/', function () {
    return view('welcome');
});

Route::group(['prefix' => 'api'], function () {

    // User
    Route::get('user/all', 'UserController@getAll');
    Route::post('user/add', 'UserController@store');
    Route::delete('user/delete/{id}', 'UserController@destroy');
    Route::post('user/update', 'UserController@update');


    // Transaction
    Route::get('transaction/all', 'TransactionController@getAll');
    Route::post('transaction/add', 'TransactionController@store');
    Route::delete('transaction/delete/{id}', 'TransactionController@destroy');
    Route::post('transaction/update', 'TransactionController@update');
    Route::post('transaction/changeState', 'TransactionController@destroy');

});