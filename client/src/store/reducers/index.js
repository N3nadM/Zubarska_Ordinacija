import { combineReducers } from "redux";
import currentUser from "./currentUser";

const appReducer = combineReducers({ currentUser });

const rootReducer = (state, action) => {
  if (action.type === "USER_LOGOUT") {
    state = undefined;
  }

  return appReducer(state, action);
};

export default rootReducer;
