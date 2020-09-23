import React from "react";
import { Provider } from "react-redux";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import { configureStore } from "./store/index";
import Home from "./components/pages/Home";
import SignIn from "./components/pages/SingIn";
import { setAuthorizationToken, setUser } from "./store/actions/auth";
import jwtDecode from "jwt-decode";

const store = configureStore();

if (localStorage.jwtToken) {
  const decodedToken = jwtDecode(localStorage.jwtToken);
  const now = new Date();
  if (Date.parse(now) / 1000 >= decodedToken.exp) {
    try {
      setAuthorizationToken(false);
      store.dispatch(setUser({}));
    } catch (err) {
      console.log(err);
    }
  } else {
    try {
      setAuthorizationToken(localStorage.jwtToken);
      store.dispatch(setUser(decodedToken));
    } catch (err) {
      store.dispatch(setUser({}));
    }
  }
}

if (localStorage.Cart && JSON.parse(localStorage.Cart).length > 0) {
  store.dispatch({
    type: "SET_CART_ITEMS_NUM",
    cartItemsNum: JSON.parse(localStorage.Cart).length,
  });
}

function App() {
  return (
    <Provider store={store}>
      <Router className="App">
        <Switch>
          <Route
            exact
            path="/login"
            render={(props) => <SignIn {...props} />}
          />
          <Route path="/*" component={Home} />
        </Switch>
      </Router>
    </Provider>
  );
}

export default App;
