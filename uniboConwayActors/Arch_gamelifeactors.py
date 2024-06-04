### conda install diagrams
from diagrams import Cluster, Diagram, Edge
from diagrams.custom import Custom
import os
os.environ['PATH'] += os.pathsep + 'C:/Program Files/Graphviz/bin/'

graphattr = {     #https://www.graphviz.org/doc/info/attrs.html
    'fontsize': '22',
}

nodeattr = {   
    'fontsize': '22',
    'bgcolor': 'lightyellow'
}

eventedgeattr = {
    'color': 'red',
    'style': 'dotted'
}
evattr = {
    'color': 'darkgreen',
    'style': 'dotted'
}
with Diagram('gamelifeactorsArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxconwayactors', graph_attr=nodeattr):
          griddisplay=Custom('griddisplay','./qakicons/symActorWithobjSmall.png')
          gamemock=Custom('gamemock','./qakicons/symActorSmall.png')
     griddisplay >> Edge( label='stopthegame', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     sys >> Edge( label='stopthegame', **evattr, decorate='true', fontcolor='darkgreen') >> gamemock
     griddisplay >> Edge(color='blue', style='solid',  decorate='true', label='<fromdisplay &nbsp; >',  fontcolor='blue') >> gamemock
     gamemock >> Edge(color='blue', style='solid',  decorate='true', label='<todisplay &nbsp; >',  fontcolor='blue') >> griddisplay
diag
