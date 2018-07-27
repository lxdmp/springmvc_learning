var cartApp = angular.module('cartApp', []);

cartApp.controller(
	'cartCtrl', 
	function($scope, $http)
	{
		$scope.entireAppName = "springtest";
		$scope.baseUrl = new Array('/'+$scope.entireAppName, 'api', 'cart').join('/')
		$scope.formatUrl = function(){
			var array = new Array(arguments.length)
			for(var i=0; i<arguments.length;++i)
				array[i] = arguments[i]
			return array.join('/');
		}

		$scope.initCartId = function(cartId) {
			$scope.cartId = cartId;
			$scope.refreshCart($scope.cartId);
		};

		$scope.refreshCart = function(cartId) {
			var url = $scope.formatUrl($scope.baseUrl, $scope.cartId);
			console.log(url)
			$http.get(url)
				.success(function(data) {
					$scope.cart = data;
				});
		};

		$scope.clearCart = function() {
			var url = $scope.formatUrl($scope.baseUrl, $scope.cartId)
			console.log(url)
			$http.delete(url)
				.success(function(data) {
					$scope.refreshCart($scope.cartId);
				});
		};

		$scope.addToCart = function(productId) {
			var url = $scope.formatUrl($scope.baseUrl, 'add', productId)
			console.log(url)
			$http.put(url)
				.success(function(data) {
					alert("Product Successfully added to the Cart!");
					$scope.refreshCart($scope.cartId);
				});
		};

		$scope.removeFromCart = function(productId) {
			var url = $scope.formatUrl($scope.baseUrl, 'remove', productId)
			console.log(url)
			$http.put(url)
				.success(function(data) {
					$scope.refreshCart($scope.cartId);
				});
		};
});

