angular.module('starter.services', [])

.factory('Chats', function() {

  var chats = [{
    id: 0,
    name: 'Ben Sparrow',
    lastText: 'You on your way?',
    face: 'https://pbs.twimg.com/profile_images/514549811765211136/9SgAuHeY.png'
  }, {
    id: 1,
    name: 'Max Lynx',
    lastText: 'Hey, it\'s me',
    face: 'https://avatars3.githubusercontent.com/u/11214?v=3&s=460'
  }];

  return {
    all: function() {
      return chats;
    },
    remove: function(chat) {
      chats.splice(chats.indexOf(chat), 1);
    },
    get: function(chatId) {
      for (var i = 0; i < chats.length; i++) {
        if (chats[i].id === parseInt(chatId)) {
          return chats[i];
        }
      }
      return null;
    }
  }
})

.factory('Friends', function($http) {
	
    var result = {};
	
    $http.get('/server/Todos?media=json').
      success(function(data, status, headers, config) {
    	for (date of data) {
    		console.log(date);
    		var key = date.id.replace("#","");
    		result[key] = {
    			id: key,
				name: date.name + " (" + date.todosCount + ")",
				header : date.name,
			    notes: 'Just the nicest guy!',
			    face: 'https://avatars3.githubusercontent.com/u/11214?v=3&s=460'
    		};
    	}
      }).
      error(function(data, status, headers, config) {
        console.log(data);
      }
    );
  
    return {
      all: function() {
        return result;
      },
      get: function(listId) {
        return result[listId];
      }
    }
  })



;
