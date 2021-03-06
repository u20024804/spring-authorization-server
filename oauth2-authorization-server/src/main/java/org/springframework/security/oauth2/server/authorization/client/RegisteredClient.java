/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.security.oauth2.server.authorization.client;

import org.springframework.security.core.SpringSecurityCoreVersion2;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * A representation of a client registration with an OAuth 2.0 Authorization Server.
 *
 * @author Joe Grandja
 * @author Anoop Garlapati
 * @see <a target="_blank" href="https://tools.ietf.org/html/rfc6749#section-2">Section 2 Client Registration</a>
 * @since 0.0.1
 */
public class RegisteredClient implements Serializable {
	private static final long serialVersionUID = SpringSecurityCoreVersion2.SERIAL_VERSION_UID;
	private String id;
	private String clientId;
	private String clientSecret;
	private Set<ClientAuthenticationMethod> clientAuthenticationMethods;
	private Set<AuthorizationGrantType> authorizationGrantTypes;
	private Set<String> redirectUris;
	private Set<String> scopes;
	private ClientSettings clientSettings;
	private TokenSettings tokenSettings;

	protected RegisteredClient() {
	}

	/**
	 * Returns the identifier for the registration.
	 *
	 * @return the identifier for the registration
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Returns the client identifier.
	 *
	 * @return the client identifier
	 */
	public String getClientId() {
		return this.clientId;
	}

	/**
	 * Returns the client secret.
	 *
	 * @return the client secret
	 */
	public String getClientSecret() {
		return this.clientSecret;
	}

	/**
	 * Returns the {@link ClientAuthenticationMethod authentication method(s)} used
	 * when authenticating the client with the authorization server.
	 *
	 * @return the {@code Set} of {@link ClientAuthenticationMethod authentication method(s)}
	 */
	public Set<ClientAuthenticationMethod> getClientAuthenticationMethods() {
		return this.clientAuthenticationMethods;
	}

	/**
	 * Returns the {@link AuthorizationGrantType authorization grant type(s)} that the client may use.
	 *
	 * @return the {@code Set} of {@link AuthorizationGrantType authorization grant type(s)}
	 */
	public Set<AuthorizationGrantType> getAuthorizationGrantTypes() {
		return this.authorizationGrantTypes;
	}

	/**
	 * Returns the redirect URI(s) that the client may use in redirect-based flows.
	 *
	 * @return the {@code Set} of redirect URI(s)
	 */
	public Set<String> getRedirectUris() {
		return this.redirectUris;
	}

	/**
	 * Returns the scope(s) used by the client.
	 *
	 * @return the {@code Set} of scope(s)
	 */
	public Set<String> getScopes() {
		return this.scopes;
	}

	/**
	 * Returns the {@link ClientSettings client configuration settings}.
	 *
	 * @return the {@link ClientSettings}
	 */
	public ClientSettings getClientSettings() {
		return this.clientSettings;
	}

	/**
	 * Returns the {@link TokenSettings token configuration settings}.
	 *
	 * @return the {@link TokenSettings}
	 */
	public TokenSettings getTokenSettings() {
		return this.tokenSettings;
	}

	@Override
	public String toString() {
		return "RegisteredClient{" +
				"id='" + this.id + '\'' +
				", clientId='" + this.clientId + '\'' +
				", clientAuthenticationMethods=" + this.clientAuthenticationMethods +
				", authorizationGrantTypes=" + this.authorizationGrantTypes +
				", redirectUris=" + this.redirectUris +
				", scopes=" + this.scopes +
				'}';
	}

	/**
	 * Returns a new {@link Builder}, initialized with the provided registration identifier.
	 *
	 * @param id the identifier for the registration
	 * @return the {@link Builder}
	 */
	public static Builder withId(String id) {
		Assert.hasText(id, "id cannot be empty");
		return new Builder(id);
	}

	/**
	 * Returns a new {@link Builder}, initialized with the provided {@link RegisteredClient}.
	 *
	 * @param registeredClient the {@link RegisteredClient} to copy from
	 * @return the {@link Builder}
	 */
	public static Builder withRegisteredClient(RegisteredClient registeredClient) {
		Assert.notNull(registeredClient, "registeredClient cannot be null");
		return new Builder(registeredClient);
	}

	/**
	 * A builder for {@link RegisteredClient}.
	 */
	public static class Builder implements Serializable {
		private static final long serialVersionUID = SpringSecurityCoreVersion2.SERIAL_VERSION_UID;
		private String id;
		private String clientId;
		private String clientSecret;
		private Set<ClientAuthenticationMethod> clientAuthenticationMethods = new LinkedHashSet<>();
		private Set<AuthorizationGrantType> authorizationGrantTypes = new LinkedHashSet<>();
		private Set<String> redirectUris = new LinkedHashSet<>();
		private Set<String> scopes = new LinkedHashSet<>();
		private ClientSettings clientSettings;
		private TokenSettings tokenSettings;

		protected Builder(String id) {
			this.id = id;
		}

		protected Builder(RegisteredClient registeredClient) {
			this.id = registeredClient.id;
			this.clientId = registeredClient.clientId;
			this.clientSecret = registeredClient.clientSecret;
			if (!CollectionUtils.isEmpty(registeredClient.clientAuthenticationMethods)) {
				this.clientAuthenticationMethods.addAll(registeredClient.clientAuthenticationMethods);
			}
			if (!CollectionUtils.isEmpty(registeredClient.authorizationGrantTypes)) {
				this.authorizationGrantTypes.addAll(registeredClient.authorizationGrantTypes);
			}
			if (!CollectionUtils.isEmpty(registeredClient.redirectUris)) {
				this.redirectUris.addAll(registeredClient.redirectUris);
			}
			if (!CollectionUtils.isEmpty(registeredClient.scopes)) {
				this.scopes.addAll(registeredClient.scopes);
			}
			this.clientSettings = new ClientSettings(registeredClient.clientSettings.settings());
			this.tokenSettings = new TokenSettings(registeredClient.tokenSettings.settings());
		}

		/**
		 * Sets the identifier for the registration.
		 *
		 * @param id the identifier for the registration
		 * @return the {@link Builder}
		 */
		public Builder id(String id) {
			this.id = id;
			return this;
		}

		/**
		 * Sets the client identifier.
		 *
		 * @param clientId the client identifier
		 * @return the {@link Builder}
		 */
		public Builder clientId(String clientId) {
			this.clientId = clientId;
			return this;
		}

		/**
		 * Sets the client secret.
		 *
		 * @param clientSecret the client secret
		 * @return the {@link Builder}
		 */
		public Builder clientSecret(String clientSecret) {
			this.clientSecret = clientSecret;
			return this;
		}

		/**
		 * Adds an {@link ClientAuthenticationMethod authentication method}
		 * the client may use when authenticating with the authorization server.
		 *
		 * @param clientAuthenticationMethod the authentication method
		 * @return the {@link Builder}
		 */
		public Builder clientAuthenticationMethod(ClientAuthenticationMethod clientAuthenticationMethod) {
			this.clientAuthenticationMethods.add(clientAuthenticationMethod);
			return this;
		}

		/**
		 * A {@code Consumer} of the {@link ClientAuthenticationMethod authentication method(s)}
		 * allowing the ability to add, replace, or remove.
		 *
		 * @param clientAuthenticationMethodsConsumer a {@code Consumer} of the authentication method(s)
		 * @return the {@link Builder}
		 */
		public Builder clientAuthenticationMethods(
				Consumer<Set<ClientAuthenticationMethod>> clientAuthenticationMethodsConsumer) {
			clientAuthenticationMethodsConsumer.accept(this.clientAuthenticationMethods);
			return this;
		}

		/**
		 * Adds an {@link AuthorizationGrantType authorization grant type} the client may use.
		 *
		 * @param authorizationGrantType the authorization grant type
		 * @return the {@link Builder}
		 */
		public Builder authorizationGrantType(AuthorizationGrantType authorizationGrantType) {
			this.authorizationGrantTypes.add(authorizationGrantType);
			return this;
		}

		/**
		 * A {@code Consumer} of the {@link AuthorizationGrantType authorization grant type(s)}
		 * allowing the ability to add, replace, or remove.
		 *
		 * @param authorizationGrantTypesConsumer a {@code Consumer} of the authorization grant type(s)
		 * @return the {@link Builder}
		 */
		public Builder authorizationGrantTypes(Consumer<Set<AuthorizationGrantType>> authorizationGrantTypesConsumer) {
			authorizationGrantTypesConsumer.accept(this.authorizationGrantTypes);
			return this;
		}

		/**
		 * Adds a redirect URI the client may use in a redirect-based flow.
		 *
		 * @param redirectUri the redirect URI
		 * @return the {@link Builder}
		 */
		public Builder redirectUri(String redirectUri) {
			this.redirectUris.add(redirectUri);
			return this;
		}

		/**
		 * A {@code Consumer} of the redirect URI(s)
		 * allowing the ability to add, replace, or remove.
		 *
		 * @param redirectUrisConsumer a {@link Consumer} of the redirect URI(s)
		 * @return the {@link Builder}
		 */
		public Builder redirectUris(Consumer<Set<String>> redirectUrisConsumer) {
			redirectUrisConsumer.accept(this.redirectUris);
			return this;
		}

		/**
		 * Adds a scope the client may use.
		 *
		 * @param scope the scope
		 * @return the {@link Builder}
		 */
		public Builder scope(String scope) {
			this.scopes.add(scope);
			return this;
		}

		/**
		 * A {@code Consumer} of the scope(s)
		 * allowing the ability to add, replace, or remove.
		 *
		 * @param scopesConsumer a {@link Consumer} of the scope(s)
		 * @return the {@link Builder}
		 */
		public Builder scopes(Consumer<Set<String>> scopesConsumer) {
			scopesConsumer.accept(this.scopes);
			return this;
		}

		/**
		 * Sets the {@link ClientSettings client configuration settings}.
		 *
		 * @param clientSettings the client configuration settings
		 * @return the {@link Builder}
		 */
		public Builder clientSettings(ClientSettings clientSettings) {
			this.clientSettings = clientSettings;
			return this;
		}

		/**
		 * Sets the {@link TokenSettings token configuration settings}.
		 *
		 * @param tokenSettings the token configuration settings
		 * @return the {@link Builder}
		 */
		public Builder tokenSettings(TokenSettings tokenSettings) {
			this.tokenSettings = tokenSettings;
			return this;
		}

		/**
		 * Builds a new {@link RegisteredClient}.
		 *
		 * @return a {@link RegisteredClient}
		 */
		public RegisteredClient build() {
			Assert.hasText(this.clientId, "clientId cannot be empty");
			Assert.notEmpty(this.authorizationGrantTypes, "authorizationGrantTypes cannot be empty");
			if (this.authorizationGrantTypes.contains(AuthorizationGrantType.AUTHORIZATION_CODE)) {
				Assert.notEmpty(this.redirectUris, "redirectUris cannot be empty");
			}
			if (CollectionUtils.isEmpty(this.clientAuthenticationMethods)) {
				this.clientAuthenticationMethods.add(ClientAuthenticationMethod.BASIC);
			}
			validateScopes();
			validateRedirectUris();
			return create();
		}

		private RegisteredClient create() {
			RegisteredClient registeredClient = new RegisteredClient();

			registeredClient.id = this.id;
			registeredClient.clientId = this.clientId;
			registeredClient.clientSecret = this.clientSecret;
			registeredClient.clientAuthenticationMethods =
					Collections.unmodifiableSet(this.clientAuthenticationMethods);
			registeredClient.authorizationGrantTypes = Collections.unmodifiableSet(this.authorizationGrantTypes);
			registeredClient.redirectUris = Collections.unmodifiableSet(this.redirectUris);
			registeredClient.scopes = Collections.unmodifiableSet(this.scopes);
			registeredClient.clientSettings = this.clientSettings != null ? this.clientSettings : new ClientSettings();
			registeredClient.tokenSettings = this.tokenSettings != null ? this.tokenSettings : new TokenSettings();

			return registeredClient;
		}

		private void validateScopes() {
			if (CollectionUtils.isEmpty(this.scopes)) {
				return;
			}

			for (String scope : this.scopes) {
				Assert.isTrue(validateScope(scope), "scope \"" + scope + "\" contains invalid characters");
			}
		}

		private static boolean validateScope(String scope) {
			return scope == null ||
					scope.chars().allMatch(c -> withinTheRangeOf(c, 0x21, 0x21) ||
							withinTheRangeOf(c, 0x23, 0x5B) ||
							withinTheRangeOf(c, 0x5D, 0x7E));
		}

		private static boolean withinTheRangeOf(int c, int min, int max) {
			return c >= min && c <= max;
		}

		private void validateRedirectUris() {
			if (CollectionUtils.isEmpty(this.redirectUris)) {
				return;
			}

			for (String redirectUri : redirectUris) {
				Assert.isTrue(validateRedirectUri(redirectUri),
						"redirect_uri \"" + redirectUri + "\" is not a valid redirect URI or contains fragment");
			}
		}

		private static boolean validateRedirectUri(String redirectUri) {
			try {
				URI validRedirectUri = new URI(redirectUri);
				return validRedirectUri.getFragment() == null;
			} catch (URISyntaxException ex) {
				return false;
			}
		}
	}
}
