FROM theasp/clojurescript-nodejs:shadow-cljs

# Configure apt
ENV DEBIAN_FRONTEND=noninteractive
RUN apt-get update \
    && apt-get -y install --no-install-recommends apt-utils 2>&1

# Install git, process tools, lsb-release (common in install instructions for CLIs)
RUN apt-get -y install git procps lsb-release rsync

# install leiningen
RUN wget https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein \
    && chmod a+x lein && mv lein /usr/local/bin/lein \
    && lein version

# Clean up
RUN apt-get autoremove -y \
    && apt-get clean -y \
    && rm -rf /var/lib/apt/lists/*
ENV DEBIAN_FRONTEND=dialog

# install joker
ENV JOKER_VERSION=0.12.4
RUN wget https://github.com/candid82/joker/releases/download/v${JOKER_VERSION}/joker-${JOKER_VERSION}-linux-amd64.zip \
    && unzip joker-${JOKER_VERSION}-linux-amd64.zip \
    && rm joker-${JOKER_VERSION}-linux-amd64.zip \
    && chmod a+x joker && mv joker /usr/bin

# npm install globally
RUN npm install -g shadow-cljs
RUN npm install -g cordova@latest

# Set the default shell to bash rather than sh
ENV SHELL /bin/bash
