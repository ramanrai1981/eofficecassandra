'use strict';
var yeoman = require('yeoman-generator');
var chalk = require('chalk');
var packagejs = require(__dirname + '/../../package.json');

// Stores JHipster variables
var jhipsterVar = { moduleName: 'darktheme' };

// Stores JHipster functions
var jhipsterFunc = {};

module.exports = yeoman.Base.extend({

    initializing: {
        compose: function(args) {
            this.composeWith('jhipster:modules', {
                    options: {
                        jhipsterVar: jhipsterVar,
                        jhipsterFunc: jhipsterFunc
                    }
                },
                this.options.testmode ? { local: require.resolve('generator-jhipster/generators/modules') } : null
            );
        },
        displayLogo: function() {
            // Have Yeoman greet the user.
            this.log('Welcome to the ' + chalk.red('JHipster darktheme') + ' generator! ' + chalk.yellow('v' + packagejs.version + '\n'));
        }
    },

    prompting: function() {
        var done = this.async();

        var prompts = [{
            type: 'confirm',
            name: 'installDesign',
            message: 'Do you want to install Dark theme?',
            default: false
        }];

        this.prompt(prompts, function(props) {
            this.props = props;
            // To access props later use this.props.someOption;

            done();
        }.bind(this));
    },

    writing: {
        writeTemplates: function() {
            this.baseName = jhipsterVar.baseName;
            this.packageName = jhipsterVar.packageName;
            this.angularAppName = jhipsterVar.angularAppName;
            var javaDir = jhipsterVar.javaDir;
            var resourceDir = jhipsterVar.resourceDir;
            var webappDir = jhipsterVar.webappDir;

            this.message = this.props.message;

            this.log('baseName=' + this.baseName);
            this.log('packageName=' + this.packageName);
            this.log('angularAppName=' + this.angularAppName);
            this.log('message=' + this.message);

            this.template('dummy.txt', 'dummy.txt', this, {});

            // Add nhstheme
            jhipsterFunc.copyTemplate('src/main/webapp/app/css/_bootstrap.min.css', 'src/main/webapp/content/css/dark.min.css', 'template', this, null, true);

        },

        registering: function() {
            try {
                jhipsterFunc.registerModule("generator-jhipster-darktheme", "entity", "post", "entity", "sample dark theme");
            } catch (err) {
                this.log(chalk.red.bold('WARN!') + ' Could not register as a jhipster entity post creation hook...\n');
            }
        }
    },

    install: function() {
        this.installDependencies();
    },

    end: function() {
        this.log('End of darktheme generator');
    }
});