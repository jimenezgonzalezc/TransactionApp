<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Http\Requests;
use App\Transaction;

class TransactionController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return Response
     */
    public function getAll() {
        return Transaction::all();
    }


    /**
     * Store a newly created resource in storage.
     *
     * @param  Request  $request
     * @return Response
     */
    public function store(Request $request) {
        $transaction = new Transaction;

        $transaction->user_id = $request->input('user_id');
        $transaction->date = $request->input('date');
        $transaction->type = $request->input('type');
        $transaction->amount = $request->input('amount');
        $transaction->state = $request->input('state');

        $transaction->save();

        return response()->json($transaction);
    }


    /**
     * Update the specified resource in storage.
     *
     * @param  Request  $request
     * @return Response
     */
    public function update(Request $request) {
        $transaction = Transaction::find($request->input('id'));

        $transaction->user_id = $request->input('user_id');
        $transaction->date = $request->input('date');
        $transaction->type = $request->input('type');
        $transaction->amount = $request->input('amount');
        $transaction->state = $request->input('state');

        $transaction->save();

        return response()->json($transaction);
    }

    
    /**
     * Remove the specified resource from storage.
     *
     * @param  $id
     * @return Response
     */
    public function destroy($id) {
        $transaction = Transaction::find($id);

        $transaction->delete();

        return response()->json($transaction);
    }

    
    /**
     * Change the state of a resource.
     *
     * @param  Request $request
     * @return Response
     */
    public function changeState(Request $request) {
        $transaction = Transaction::find($request->input('id'));

        $transaction->estado = $request->input('state');

        $transaction->save();

        return response()->json($transaction);
    }
}
