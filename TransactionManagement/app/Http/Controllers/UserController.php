<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Http\Requests;
use App\User;

class UserController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return Response
     */
    public function getAll() {
        return User::all();
    }


    /**
     * Store a newly created resource in storage.
     *
     * @param  Request  $request
     * @return Response
     */
    public function store(Request $request) {
        $user = new User;

        $user->user = $request->input('user');
        $user->email = $request->input('email');
        $user->password = $request->input('password');
        $user->debit = $request->input('debit');

        $user->save();
        
        return response()->json($user);
    }


    /**
     * Update the specified resource in storage.
     *
     * @param  Request  $request
     * @return Response
     */
    public function update(Request $request) {
        $user = User::find($request->input('id'));

        $user->user = $request->input('user');
        $user->email = $request->input('email');
        $user->password = $request->input('password');
        $user->debit = $request->input('debit');

        $user->save();

        return response()->json($user);
    }


    /**
     * Remove the specified resource from storage.
     *
     * @param  $id
     * @return Response
     */
    public function destroy($id) {
        $user = User::find($id);

        $user->delete();

        return response()->json($user);
    }
}
