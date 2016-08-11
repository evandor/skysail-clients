angular.module('starter.controllers', [])

.controller('DashCtrl', function($scope) {})

.controller('ChatsCtrl', function($scope, Chats) {
  $scope.chats = Chats.all();
  $scope.remove = function(chat) {
    Chats.remove(chat);
  }
})

.controller('ChatDetailCtrl', function($scope, $stateParams, Chats) {
  $scope.chat = Chats.get($stateParams.chatId);
})

.controller('ListsCtrl', function($scope, Friends) {
  $scope.friends = Friends.all();
})

.controller('FriendDetailCtrl', function($scope, $stateParams, Todos2) {
	$scope.listId = $stateParams.listId;
    $scope.todos = Todos2.get($scope, $stateParams.listId);
})

.controller('AccountCtrl', function($scope) {
  $scope.settings = {
    enableFriends: true
  };
})

.controller('LoginCtrl', function($scope, LoginService, $ionicPopup, $state, AuthService) {
    $scope.data = {};
    
    $scope.login = function(data) {
    	AuthService.login(data.username, data.password).then(function(authenticated) {
          $state.go('tab.lists', {}, {reload: true});
         // $scope.setCurrentUsername(data.username);
        }, function(err) {
          var alertPopup = $ionicPopup.alert({
            title: 'Login failed!',
            template: 'Please check your credentials!'
          });
        });
      };
 
});
