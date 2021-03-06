/*
 * This file is part of Discord4J.
 *
 * Discord4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Discord4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Discord4J.  If not, see <http://www.gnu.org/licenses/>.
 */
package discord4j.core.object.entity.channel;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.data.stored.ChannelBean;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Snowflake;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/** A Discord private channel (also known as a DM channel). */
public final class PrivateChannel extends BaseMessageChannel {

    /**
     * Constructs an {@code PrivateChannel} with an associated ServiceMediator and Discord data.
     *
     * @param gateway The {@link GatewayDiscordClient} associated to this object, must be non-null.
     * @param data The raw data as represented by Discord, must be non-null.
     */
    public PrivateChannel(final GatewayDiscordClient gateway, final ChannelBean data) {
        super(gateway, data);
    }

    /**
     * Gets the IDs of the recipients for this private channel.
     *
     * @return The IDs of the recipients for this private channel.
     */
    public Set<Snowflake> getRecipientIds() {
        return Arrays.stream(Objects.requireNonNull(getData().getRecipients()))
                .mapToObj(Snowflake::of)
                .collect(Collectors.toSet());
    }

    /**
     * Requests to retrieve the recipients for this private channel.
     *
     * @return A {@link Flux} that continually emits the {@link User recipients} for this private channel. If an error
     * is received, it is emitted through the {@code Flux}.
     */
    public Flux<User> getRecipients() {
        return Flux.fromIterable(getRecipientIds()).flatMap(getClient()::getUserById);
    }

    @Override
    public String toString() {
        return "PrivateChannel{} " + super.toString();
    }
}
