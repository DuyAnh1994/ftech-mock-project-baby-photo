package ai.ftech.babyphoto.model

class DataResult<T> {

    var state: State = State.SUCCESS

    enum class State {
        INITIAL, SUCCESS, FAIL, ERROR
    }

}