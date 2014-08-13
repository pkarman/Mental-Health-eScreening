/**
 * Created by Bryan Henderson on 5/13/2014.
 */


/////////////////////////////////////////////////////////////////////
// FTL Markup Constants - based on Freemarker in-built directives. //
/////////////////////////////////////////////////////////////////////

/******** Directives ********/
// NOTE:: This is a subset implementation, so all of the available built-in FTL tags will not be represented.  For reference, they are in the commented area below.

/*var FTL_ASSIGN = "#assign";
var FTL_ATTEMPT = "#attempt";
var FTL_BREAK_SWITCH = "#break";
var FTL_BREAK_LIST = "#list";
var FTL_CASE = "#case";
var FTL_COMPRESS = "#compress";
var FTL_DEFAULT = "#default";
var FTL_ELSE = "#else";
var FTL_ELSEIF = "#elseif";
var FTL_ESCAPE = "#escape";
var FTL_FALLBACK = "#fallback";
var FTL_FUNCTION = "#function";
var FTL_FLUSH = "#flush";
var FTL_FTL = "#ftl";
var FTL_GLOBAL = "#global";
var FTL_IF = "#if";
var FTL_INCLUDE = "#include";
var FTL_IMPORT = "#import";
var FTL_LIST_REG = "#list";
var FTL_LOCAL = "#local";
var FTL_LEFT_TRIM = "#lt";
var FTL_MACRO = "#macro";
var FTL_NESTED = "#nested";
var FTL_NOESCAPE = "#noescape";
var FTL_NO_TRIM = "#nt";
var FTL_RECOVER = "#recover";
var FTL_RECURSE = "#recurse";
var FTL_RETURN = "#return";
var FTL_RIGHT_TRIM = "#rt";
var FTL_SETTING = "#setting";
var FTL_STOP = "#stop";
var FTL_SWITCH = "#switch";
var FTL_TRIM = "#t";
var FTL_VISIT = "#visit";*/


function FTLTranslator() {
    // FTL RegEx globals.
    this.trimPattern = /<#t>/g;
    this.titlePattern = /\${TITLE_START}(.*?)\${TITLE_END}/g;
    this.sectionPattern = /\${SECTION_START}(.*?)\${SECTION_END}/g;
    this.commentPattern = /<#--(.*?)-->/g;
    this.assignPattern = /<#assign(.*?)>/g;
    this.includePattern = /<#include(.*?)>/g;
    this.ifPattern = /<#if (.*?)>/g;
    this.elsePattern = /<#else>/g;
    this.elseIfPattern = /<#elseif(.*?)>/g;
    this.newLine = /\n\r/g;

    /* -- Document variables, global, for HTML and FTL text representations -- */
    this.ftlDoc = '';
    this.htmlDoc = '';
    this.title = '';
    this.include = '';
    /* -- End Document variables -- */

    /* -- Document Title -- */
    this.docTitle = '';
    /* -- Includes -- */
    this.docIncludes = [];
    /* -- Document Setters -- */
    this.setFTLDoc = function(ftlString /* String representation of FTL stream */)/* returns null */{
        this.ftlDoc = ftlString;
    };
    this.setHTMLDoc = function(htmlString /* String representation of HTML stream */)/* returns null */{
        this.htmlDoc = htmlString;
    };

    this.setTitle = function(title /* String representation of document title */) /* returns null */{
        this.title = title;
    }

    this.setInclude = function(include /* String representation of document include */)/* returns null j*/{
        this.include = include;
    }

    this.getTitle = function() /* returns String */{
        return this.title;
    }

    this.getInclude = function() /* returns String */ {
        return this.include;
    }
    /* -- End Document Setters -- */
}


/** -- convert: Gateway Method for FTL/HTML output (stand-in for Constructor): Intakes the FTL/HTML string, converts, and returns the HTML/FTL string as converted.
 *
 * @param section
 * @returns {string}
 */
FTLTranslator.prototype.convert = function(outputType /* 'ftl' or 'html'*/, text /* String of HTML-encoded, or FTL-encoded text*/)/* returns Object (HTML or FTL, driven by type)*/{
    if (outputType && outputType !== ''){
        if (outputType == 'html'){
            // Input text is FTL-encoded.  Convert it to HTML.
            this.setFTLDoc(text);
            // Set title.
            this.title = this.pullTitleForHTML(text);
            // Set includes.
            this.include = this.pullIncludeForHTML(text);
            // Grab Section(s).
            var sections = this.sectionToHTML(text);

            return {title:this.title,include:this.include,sections:sections};
        }else if (outputType == 'ftl'){
            // Input text is HTML-encoded.  Convert it to FTL.
            this.setHTMLDoc(text);
            var ftlDoc = this.sectionToFTL(text);
            return {title:this.title, include:this.include, ftl:ftlDoc};
        }else{
            throw new Error("Output type must be either 'ftl' or 'html'!");
        }
    }else{
        throw(new Error('Output type is required!'));
    }
};

/* -- Page Sectional Elements translation methods.  These are public.
The rest you'll just have to be on your honor not to use. -- */
FTLTranslator.prototype.sectionToHTML = function(section /* FTL <#assign>*/)/* returns HTML as string*/{
    var html = '';
    // Eliminate Title(s), Section brackets.  You already have what you need from them.
    section = section.replace(this.titlePattern,'');
    section = section.replace(this.includePattern,'');
    section = section.replace(/\${SECTION_START}/g,'');
    section = section.replace(/\${SECTION_END}/g,'');

    // Now, remove brackets from function calls, etc.
    section = section.replace(/\${/g,'<span class="value">VALUE OF: ');
    section = section.replace(/}/g,'</span>');
    section = section.replace(this.trimPattern,'<span class="trim">TRIM VALUE</span>');
    // Align comments to HTML format.
    section = section.replace(/<#--/g,'<!--');
    // Now, grab the assign opening tag.
    var sAssign = section.match(this.assignPattern);
    var assignRE = /<#assign([^}]+)>/g;
    sAssign = assignRE.exec(sAssign[0]);

    html += '<div class="section"><span class="assign">Section: ' + sAssign[1] + '</span>';

    // Change '>=' statements to '@@'.
    section = section.replace(/>=/g,'@@');
    // Discard the <#assign> from section.
    section = section.replace(this.assignPattern,'').replace(/<\/#assign>/g,'');
    // Replace <#else> tags with classed <div>s
    section =  section.replace(this.elsePattern,'<div class="block else">ELSE</div>');
    // Handle <#/if> tags by replacement with </div>s
    //section = section.replace(/<\/#if>/g,'</div>');
    // Handle <#if tags by elimination/replacement with <div> tags and expression content.

    var ifReg = /<#if(.*?)>/g;
    var arrIf = section.match(ifReg);
    for (var i=0;i<arrIf.length;i++){

        arrIf[i] = arrIf[i].replace('<#if','IF: ');
        arrIf[i] = arrIf[i].replace('>','');
    }

    i=0;
    //section = section.replace(this.ifPattern,'<div class="tag if"></div>');
    section = section.replace(this.ifPattern,function(){
       var sReplace = '<div class="block if">' + arrIf[i] + '</div>';
       i++;
       return sReplace;
    });

    // Handle <#elseif tags by elimination/replacement with <div> tags and expression content.
    // Close the Section <div> before returning.
    var elseReg = /<#elseif(.*?)>/g;
    var arrElse = section.match(elseReg);

    for (i=0;i<arrElse.length;i++){
        arrElse[i] = arrElse[i].replace('<#elseif', 'ELSE IF: ');
        arrElse[i] = arrElse[i].substr(0,arrElse[i].length - 1);
    }
    i=0;
    section = section.replace(this.elseIfPattern,function(){
        var sReplace = '<div class="block elseif">'+ arrElse[i] + '</div>';
        i++;
        return sReplace;
    });
    section = section.replace(this.ifPattern,'');
    section = section.replace(/<\/#if>/g,'');
    section = section.replace(this.elseIfPattern,'');
    section = section.replace(/<\/#elseif>/g,'');
    // Change '@@' back to '>='.
    section = section.replace(/@@/g, '>=');
    html += section;
    //html += '</div>';
    return html;
};

FTLTranslator.prototype.sectionToFTL = function(html /* The HTML String to convert to FTL */) /* returns FTL representation of the HTML String parameter */{
    var sectionRE = /<div class="section">/g;
    var assignRE = /<span class="assign">SECTION: /g;
    var ifRE = /<div class="block if">IF:/g;
    var elseRE = /<div class="block else">ELSE<\/div>/g;
    var elseIfRE = /<div class="block elseif">ELSE IF:/g;
    var trimRE = /<span class="trim">TRIM VALUE<\/span>/g;
    var valueRE = /<span class="value">VALUE OF:/g;
    var lastDivRE = /([\s\S]*)<\/div>/; // Possible

    var ftlDoc = '';
    // Change comments to FTL format.
    html = html.replace(/<!--/g, '<#--');
    // Add the #include back in.
    var inclStr = '<#include "' + this.include + '"> ';
    // Add the title back in.
    var titleStr = '${TITLE_START}' + this.title + '${TITLE_END}';
    html = titleStr + html;
    html = inclStr + html;

    // Start pattern-matching.
    // Grab the #assign text.
    var strAssign = html.match(/<span class="assign">Section: (.*?)<\/span>/);
    strAssign = strAssign[0].replace(/<span class="assign">Section: /g,'');
    strAssign = strAssign.replace(/<\/span>/g,'');
    html = html.replace(/<span class="assign">Section: (.*?)<\/div>/, '<#assign ' + strAssign + '> ');
    // Replace Section <div> junk.
    html = html.replace(sectionRE, '${SECTION_START}');
    html = html.substr(0, html.length - 6);
    html += '${SECTION_END}';
    // Add back the <#t> tags.
    html = html.replace(trimRE, '<#t>');
    // Replace ELSE <div>s
    html = html.replace(elseRE,'<#else> ');
    html = html.replace(valueRE, '${');
    html = html.replace(/<\/span>/g, '}');

    // Convert </divs> to </#if>
    html = html.replace(/<\/div>/g,'</#if>');
    // Convert ELSEIFs.
    var arrElseIf = html.match(/<div class="block elseif">(.*?)<\/#if>/g);
    for (var i=0;i<arrElseIf.length;i++){
        arrElseIf[i] = arrElseIf[i].replace(elseIfRE,'');
        arrElseIf[i] = arrElseIf[i].replace(/<\/#if>/g, '');
       // arrElseIf[i] = arrElseIf[i] + ' </#if>';
    }
    i=0;
    html = html.replace(/<div class="block elseif">(.*?)<\/#if>/g, function(){
        var sReplace = '<#elseif ' + arrElseIf[i] + '>';
        i++;
        return sReplace;
    });
    // Close <if> after period.
    html = html.replace(/\./g,'. </#if>');
    // Convert IFs
    // Convert the end tags.
    var arrIf = html.match(/<div class="block if">IF:(.*?)<\/#if>/g);
    //html = html.replace(/<\/div>/g,'</#if>');
    i=0;
    for (i=0; i<arrIf.length;i++){
        arrIf[i] = arrIf[i].replace(ifRE,'');
        arrIf[i] = arrIf[i].replace(/<\/#if>/g,'');
       // alert(arrIf[i]);
    }
    i=0;
    html = html.replace(/<div class="block if">IF:(.*?)<\/#if>/g, function(){
        var sReplace = '<#if '+arrIf[i]+'> ';
        i++;
        return sReplace;
    });

    // Assign for output.
    ftlDoc = html;
    return ftlDoc;
};

FTLTranslator.prototype.pullTitleForHTML = function(ftl /* FTL ${TITLE_START},${TITLE_END}, and content */)/* returns HTML as string */{
    var titleEx = /\${TITLE_START}(.*?)\${TITLE_END}/g;
    var title = ftl.match(titleEx);
    title = title[0].replace(/\${TITLE_START}/g,'');
    title = title.replace(/\${TITLE_END}/g,'');
    return title;
};

FTLTranslator.prototype.pullIncludeForHTML = function(ftl /* FTL <#include> and attribute content (filename) */)/* returns HTML as string */{
    var html;
    html = ftl.match(/<#include(.*?)>/g);
    html = html[0].replace(/<#include "/g,'');
    html = html.replace(/">/g,'');
    return html;
};

FTLTranslator.prototype.pushSectionToFTL = function(sectionHTML /* Takes HTML Section*/)/* returns FTL Section */{
    var ftl;
    return ftl;
};

FTLTranslator.prototype.pushIncludeToFTL = function(includeHTML /* Takes HTML Include */)/* returns FTL #include, with internal content */{
    var ftl;
    return ftl;
};

FTLTranslator.prototype.pushTitleToFTL = function(titleHTML /* Takes HTML Title */)/* returns FTL ${TITLE}, with internal content */{
    var ftl;
    return ftl;
};
/* -- End Sectional Elements translation methods. -- */

/* -- FTL Tag-to-HTML methods -- */
FTLTranslator.prototype.trimText = function(text /* text string */)/* returns cleaned string */ {
    return text.trim();
};